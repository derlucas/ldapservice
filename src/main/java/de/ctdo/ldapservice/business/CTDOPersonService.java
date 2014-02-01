package de.ctdo.ldapservice.business;

import de.ctdo.ldapservice.model.Person;
import de.ctdo.ldapservice.utils.Helper;
import de.ctdo.ldapservice.utils.SSHA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

@Service
public class CTDOPersonService implements PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CTDOPersonService.class);
    private static final String BASE_PEOPLE = "ou=people";
    private final PersonContextMapper personContextMapper = new PersonContextMapper();

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage simpleMailMessage;


    @Override
    public Person create(Person person) {

        DistinguishedName dn = new DistinguishedName();
        dn.add("ou", "people");
        dn.add("cn", person.getUid());

        person.setGroupId("2000");
        person.setUidNumber(getNextFreeUserId() + "");
        person.setPasswordSSHA(SSHA.getInstance().createDigest(person.getPassword()));

        DirContextAdapter context = new DirContextAdapter(dn);
        mapToContext(person, context);
        ldapTemplate.bind(context);

        sendEmail(person);

        return person;
    }

    private void sendEmail(Person person) {
        SimpleMailMessage msg = new SimpleMailMessage(simpleMailMessage);
        msg.setText("Added new user " + person);
        msg.setSubject("CTDO LDAP Self Service New user");

        try {
            this.mailSender.send(msg);
        } catch (Exception ex) {
            LOGGER.error("could not send email", ex);
        }
    }


    @Override
    public boolean isEmailTaken(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new WhitespaceWildcardsFilter("mail", email));
        return ldapTemplate.search(BASE_PEOPLE, filter.encode(), personContextMapper).size() > 0;
    }

    @Override
    public boolean isUidTaken(String uid) {
        if (StringUtils.isEmpty(uid)) {
            return false;
        }
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new WhitespaceWildcardsFilter("uid", uid));
        return ldapTemplate.search(BASE_PEOPLE, filter.encode(), personContextMapper).size() > 0;
    }

    private int getNextFreeUserId() {
        final EqualsFilter filter = new EqualsFilter("objectclass", "person");

        List list = ldapTemplate.search(BASE_PEOPLE, filter.encode(),
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs) throws NamingException {
                        if (attrs.get("uidNumber") != null) {
                            return attrs.get("uidNumber").get();
                        }
                        return null;
                    }
                });

        return Helper.getMaxIntInList(list) + 1;
    }

    private void mapToContext(Person person, DirContextOperations context) {
        context.setAttributeValues("objectclass", new String[]{"top", "inetOrgPerson", "posixAccount"});
        context.setAttributeValue("cn", person.getUid());
        context.setAttributeValue("sn", person.getLastName());
        context.setAttributeValue("givenName", person.getFirstName());
        context.setAttributeValue("displayName", person.getFirstName());
        context.setAttributeValue("loginShell", "/bin/bash");
        context.setAttributeValue("mail", person.getEmailAddress());
        context.setAttributeValue("homeDirectory", "/home/" + person.getUid());
        context.setAttributeValue("uid", person.getUid());
        context.setAttributeValue("uidNumber", person.getUidNumber());
        context.setAttributeValue("gidNumber", person.getGroupId());
        context.setAttributeValue("userPassword", person.getPasswordSSHA());
    }

    private static class PersonContextMapper extends AbstractContextMapper {
        public Object doMapFromContext(DirContextOperations context) {
            Person person = new Person();
            person.setLastName(context.getStringAttribute("sn"));
            person.setFirstName(context.getStringAttribute("givenName"));
            person.setEmailAddress(context.getStringAttribute("mail"));
            person.setGroupId(context.getStringAttribute("gidNumber"));
            person.setUidNumber(context.getStringAttribute("uidNumber"));
            person.setUid(context.getStringAttribute("uid"));
            return person;
        }
    }
}
