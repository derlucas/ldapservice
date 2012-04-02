package de.ctdo.ldapservice.dao;

import de.ctdo.ldapservice.business.Helper;
import de.ctdo.ldapservice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.stereotype.Repository;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {
    private LdapTemplate ldapTemplate;

    @Autowired
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public int getNextFreeGroupId() {
        List bla = getListByAttribute("gidNumber");
        return Helper.getMaxIntInList(getListByAttribute("gidNumber")) + 1;
    }

    @Override
    public int getNextFreeUserId() {
        return Helper.getMaxIntInList(getListByAttribute("uidNumber")) + 1;
    }


    private List getListByAttribute(final String attribute) {
        List list = ldapTemplate.search("", "(objectclass=inetOrgPerson)",
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs) throws NamingException {
                        if(attrs.get(attribute) != null) {
                            return attrs.get(attribute).get();
                        }
                        return null;
                    }
                });
        return list;
    }



    @Override
    public void create(Person person) {
        DirContextAdapter context = new DirContextAdapter(buildDn(person));
        mapToContext(person, context);
        ldapTemplate.bind(context);
    }

    @Override
    public void update(Person person) {
        Name dn = buildDn(person);
        DirContextOperations context = ldapTemplate.lookupContext(dn);
        mapToContext(person, context);
        ldapTemplate.modifyAttributes(context);
    }

    @Override
    public void delete(Person person) {
        ldapTemplate.unbind(buildDn(person));
    }

    @Override
    public Person findByPrimaryKey(String firstName, String lastName) {
        Name dn = buildDn(firstName, lastName);
        return (Person) ldapTemplate.lookup(dn, getContextMapper());
    }

    @Override
    public List findByName(String name) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "inetOrgPerson")).and(new WhitespaceWildcardsFilter("cn", name));
        return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
    }

    @Override
    public List findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass", "inetOrgPerson");
        return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
    }

    private ContextMapper getContextMapper() {
        return new PersonContextMapper();
    }

    private Name buildDn(Person person) {
        return buildDn(person.getFirstName(), person.getLastName());
    }

    private Name buildDn(String firstName, String lastName) {
        DistinguishedName dn = new DistinguishedName();
//        dn.add("dc", "de");
//        dn.add("dc", "chaostreff-dortmund");
        dn.add("ou", "people");
        dn.add("cn", firstName + " " + lastName);
        return dn;
    }

    private void mapToContext(Person person, DirContextOperations context) {
        context.setAttributeValues("objectclass", new String[]{"top", "person", "organizationalPerson","inetOrgPerson","posixAccount","shadowAccount"});
        context.setAttributeValue("cn", person.getFullName());
        context.setAttributeValue("sn", person.getLastName());
        context.setAttributeValue("givenName", person.getFirstName());
        //context.setAttributeValue("gender", person.getGender().toUpperCase());
        context.setAttributeValue("mail", person.getEmailAddress());
        context.setAttributeValue("homeDirectory", person.getHomeDirectory());
        context.setAttributeValue("uid", person.getUserName());
        context.setAttributeValue("uidNumber", person.getUserId());
        context.setAttributeValue("gidNumber", person.getGroupId());
//        if(person.getBirthDate() != null) {
//            context.setAttributeValue("dateOfBirth", person.getBirthDate());
//        }
    }

    private static class PersonContextMapper extends AbstractContextMapper {
        public Object doMapFromContext(DirContextOperations context) {
            Person person = new Person();
            person.setLastName(context.getStringAttribute("sn"));
            person.setFirstName(context.getStringAttribute("givenName"));
            person.setGender(context.getStringAttribute("gender"));

            //person.setBirthDate(context.getStringAttribute("dateOfBirth")); TODO

            person.setEmailAddress(context.getStringAttribute("mail"));
            person.setGroupId(context.getStringAttribute("gidNumber"));
            person.setHomeDirectory(context.getStringAttribute("homeDirectory"));
            person.setUserId(context.getStringAttribute("uidNumber"));
            person.setUserName(context.getStringAttribute("uid"));

            return person;
        }
    }
}
