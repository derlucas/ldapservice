package de.ctdo.ldapservice.dao;

import de.ctdo.ldapservice.business.Helper;
import de.ctdo.ldapservice.business.SSHA;
import de.ctdo.ldapservice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.stereotype.Repository;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {
    public static final String BASE_PEOPLE = "ou=people";
    private PersonContextMapper personContextMapper = new PersonContextMapper();

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public int getNextFreeUserId() {
        return Helper.getMaxIntInList(getListByAttribute("uidNumber")) + 1;
    }

    private List getListByAttribute(final String attribute) {
        final EqualsFilter filter = new EqualsFilter("objectclass", "inetOrgPerson");

        return ldapTemplate.search(BASE_PEOPLE, filter.encode(),
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs) throws NamingException {
                        if (attrs.get(attribute) != null) {
                            return attrs.get(attribute).get();
                        }
                        return null;
                    }
                });
    }


    @Override
    public Person create(Person person) {

        //if(!isEmailTaken(person.getEmailAddress()) && !isUidTaken(person.getUid())) {
            DistinguishedName dn = new DistinguishedName();
            dn.add("ou", "people");
            dn.add("cn", person.getUid());

            person.setGroupId("2000");
            person.setUidNumber(getNextFreeUserId() + "");

            person.setPasswordSSHA(SSHA.getInstance().createDigest(person.getPassword()));

            DirContextAdapter context = new DirContextAdapter(dn);
            mapToContext(person, context);
            ldapTemplate.bind(context);
            return person;
//        }
//
//        return null;
    }


    @Override
    public boolean isEmailTaken(String email) {
        if("".contentEquals(email)) return false;
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new WhitespaceWildcardsFilter("mail", email));
        return ldapTemplate.search(BASE_PEOPLE, filter.encode(), personContextMapper).size() > 0;
    }

    @Override
    public boolean isUidTaken(String uid) {
        if("".contentEquals(uid)) return false;
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new WhitespaceWildcardsFilter("uid", uid));
        return ldapTemplate.search(BASE_PEOPLE, filter.encode(), personContextMapper).size() > 0;
    }


    private void mapToContext(Person person, DirContextOperations context) {
        context.setAttributeValues("objectclass", new String[]{"top", "inetOrgPerson", "posixAccount"});
        context.setAttributeValue("cn", person.getUid());
        context.setAttributeValue("sn", person.getLastName());
        context.setAttributeValue("givenName", person.getFirstName());
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
