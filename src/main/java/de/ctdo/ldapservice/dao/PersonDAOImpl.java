package de.ctdo.ldapservice.dao;

import javax.naming.Name;
import javax.naming.NamingException;

import de.ctdo.ldapservice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.*;
import org.springframework.stereotype.Repository;

import javax.naming.directory.*;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {
    public static final String BASE_DN = "dc=chaostreff-dortmund,dc=de"; //TODO: weg hier
    private LdapTemplate ldapTemplate;

    @Autowired
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public List getAllNames() {
        return ldapTemplate.search("", "(objectclass=inetOrgPerson)",
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs) throws NamingException {
                        return attrs.get("cn").get();
                    }
                });
    }

    @Override
    public List<Person> getAll() {
        return ldapTemplate.search("", "(objectclass=inetOrgPerson)", new PersonAttributesMapper());
    }

    @Override
    public void create(Person p) {
        Name dn = buildDn(p);
        ldapTemplate.bind(dn, null, buildAttributes(p));
    }

    @Override
    public int getNextFreeGroupId() {
        return getMaxIntInList(getListByAttribute("gidNumber")) + 1;
    }

    @Override
    public int getNextFreeUserId() {
        return getMaxIntInList(getListByAttribute("uidNumber")) + 1;
    }



    private int getMaxIntInList(List list) {
        int maxId = 0;
        for(Object uidS: list) {
            int uid = Integer.parseInt(uidS.toString());
            maxId = uid > maxId ? uid : maxId;
        }
        return maxId;
    }

    private List getListByAttribute(final String attribute) {
        List list = ldapTemplate.search("", "(objectclass=inetOrgPerson)",
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs) throws NamingException {
                        return attrs.get(attribute).get();
                    }
                });
        return list;
    }

    private class PersonAttributesMapper implements AttributesMapper {
        public Object mapFromAttributes(Attributes attrs) throws NamingException {
            Person person = new Person();
            person.setFirstName((String) attrs.get("givenName").get());
            person.setLastName((String)attrs.get("sn").get());

            return person;
        }
    }

    private Attributes buildAttributes(Person p) {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocattr = new BasicAttribute("objectclass");
        ocattr.add("top");
        ocattr.add("inetOrgPerson");
        attrs.put(ocattr);
        attrs.put("cn", p.getFullName());
        attrs.put("sn", p.getLastName());
        attrs.put("givenName", p.getFirstName());
        attrs.put("gender", p.getGender().toUpperCase());
        attrs.put("mail", p.getEmailAddress());
        attrs.put("homeDirectory", p.getHomeDirectory());
        attrs.put("uid", p.getUserName());
        attrs.put("uidNumber", p.getUserId());
        attrs.put("gidNumber", p.getGroupId());
        return attrs;
    }

    protected Name buildDn(Person p) {
        DistinguishedName dn = new DistinguishedName(BASE_DN);
        dn.add("ou", "people");
        dn.add("cn", p.getFullName());
        return dn;
    }

}
