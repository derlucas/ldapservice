package de.ctdo.ldapservice;

import de.ctdo.ldapservice.dao.PersonDAO;
import de.ctdo.ldapservice.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

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
        assertNotNull("list is null", dao.getAll());
    }

    @Test
    public void getAllNames() {
        assertNotNull("list is null", dao.getAllNames());
    }

    @Test
    public void getNextFreeGroupId() {
        assertTrue("groupId not greater 0", dao.getNextFreeGroupId() > 0);
    }

    @Test
    public void getNextFreeUserId() {
        assertTrue("userID not greater 0", dao.getNextFreeUserId() > 0);
    }

}
