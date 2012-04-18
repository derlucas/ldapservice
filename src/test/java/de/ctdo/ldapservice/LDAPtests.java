package de.ctdo.ldapservice;

import de.ctdo.ldapservice.dao.PersonDAO;
import de.ctdo.ldapservice.model.Person;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.*;

@ContextConfiguration(locations = { "classpath:META-INF/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LDAPtests {

    @Autowired
    PersonDAO dao;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getAll() {
        assertNotNull("list is null", dao.findAll());
        
        List bla = dao.findAll();
        
    }


//    @Test
//    public void addUser() {
//        Person person = new Person();
//        person.setLastName("Nord");
//        person.setFirstName("Frank");
//        person.setGender("M");
//        person.setBirthDate(new DateTime());
//        person.setEmailAddress("frank.nord@example.com");
//        person.setGroupId(dao.getNextFreeGroupId());
//        person.setUidNumber(dao.getNextFreeUserId());
//        person.setUid("fnord");
//
//        dao.create(person);
//    }
    
    @Test
    public void findUser() {
        Person user = dao.findByPrimaryKey("Frank", "Nord");
        assertNotNull("user not found", user);
    }

//    @Test(expected = NameNotFoundException.class)
//    public void deleteUser()  {
//        Person user = dao.findByPrimaryKey("Frank", "Nord");
//        assertNotNull("user not found", user);
//        dao.delete(user);
//
//        dao.findByPrimaryKey("Frank", "Nord");
//    }

    @Test
    public void getNextFreeGroupId() {
        assertTrue("groupId not greater 0", dao.getNextFreeGroupId() > 0);
    }

    @Test
    public void getNextFreeUserId() {
        assertTrue("userID not greater 0", dao.getNextFreeUserId() > 0);
    }

}
