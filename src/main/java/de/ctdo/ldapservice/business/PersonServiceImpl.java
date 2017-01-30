package de.ctdo.ldapservice.business;

import de.ctdo.ldapservice.configuration.AppConfig;
import de.ctdo.ldapservice.configuration.EmailConfig;
import de.ctdo.ldapservice.model.Person;
import de.ctdo.ldapservice.utils.Helper;
import de.ctdo.ldapservice.utils.SSHA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.naming.Name;
import java.util.List;
import java.util.Optional;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PersonServiceImpl implements PersonService {

    private static final String BASE_PEOPLE = "ou=people";

    private final PersonContextMapper personContextMapper = new PersonContextMapper();
    private final AppConfig appConfig;
    private final EmailConfig emailConfig;
    private final LdapTemplate ldapTemplate;
    private final MailSender mailSender;

    @Override
    public Optional<Person> create(Person person) {

        person.setGroupId(appConfig.getDefaultGID());
        person.setUidNumber(getNextFreeUserId() + "");
        person.setPasswordSSHA(SSHA.getInstance().createDigest(person.getPassword()));

        Name dn = LdapNameBuilder.newInstance()
                                 .add("ou", appConfig.getUserOU())
                                 .add("cn", person.getUid()).build();

        DirContextAdapter context = new DirContextAdapter(dn);
        mapToContext(person, context);
        ldapTemplate.bind(context);

        sendEmail(person);

        return Optional.of(person);
    }

    private void sendEmail(Person person) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(emailConfig.getSubject());
        msg.setFrom(emailConfig.getFrom());
        msg.setText("Added new user " + person);
        msg.setSubject("CTDO LDAP Self Service New user");

        try {
            this.mailSender.send(msg);
        }
        catch (Exception ex) {
            log.error("could not send email", ex);
        }
    }

    @Override
    public boolean isEmailTaken(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }

        return ldapTemplate.search(
            query().base(BASE_PEOPLE).where("objectclass").is("person").and("mail").is(email),
            personContextMapper).size() > 0;
    }

    @Override
    public boolean isUidTaken(String uid) {
        if (StringUtils.isEmpty(uid)) {
            return false;
        }

        return ldapTemplate.search(
            query().base(BASE_PEOPLE).where("objectclass").is("person").and("uid").is(uid),
            personContextMapper).size() > 0;
    }

    private int getNextFreeUserId() {
        List<String> list = ldapTemplate.search(
            query().base(BASE_PEOPLE).attributes("uidNumber")
                   .where("objectclass").is("person").and("uidNumber").isPresent(),
            (AttributesMapper<String>) attributes -> attributes.get("uidNumber").get().toString()
        );

        return Helper.getMaxIntInList(list);
    }

    private void mapToContext(Person person, DirContextOperations context) {
        context.setAttributeValues("objectclass", new String[]{"top", "inetOrgPerson", "posixAccount"});
        context.setAttributeValue("cn", person.getUid());
        context.setAttributeValue("sn", person.getLastName());
        context.setAttributeValue("givenName", person.getFirstName());
        context.setAttributeValue("displayName", person.getFirstName());
        context.setAttributeValue("loginShell", person.getLoginShell());
        context.setAttributeValue("mail", person.getEmailAddress());
        context.setAttributeValue("homeDirectory", "/home/" + person.getUid());
        context.setAttributeValue("uid", person.getUid());
        context.setAttributeValue("uidNumber", person.getUidNumber());
        context.setAttributeValue("gidNumber", person.getGroupId());
        context.setAttributeValue("userPassword", person.getPasswordSSHA());
    }

    private static class PersonContextMapper extends AbstractContextMapper<Person> {

        public Person doMapFromContext(DirContextOperations context) {
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
