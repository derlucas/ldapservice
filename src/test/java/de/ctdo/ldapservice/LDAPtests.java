package de.ctdo.ldapservice;

import de.ctdo.ldapservice.dao.PersonDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void getNextFreeUserId() {
        assertTrue("userID not greater 0", dao.getNextFreeUserId() > 0);
    }

    @Test
    public void testIsEmailTaken() {
        boolean result = dao.isEmailTaken("punkt@ctdo.de");

        assertTrue("should be taken", result);
    }

    @Test
    public void testIsUIDTaken() {
        boolean result = dao.isUidTaken("lucas");

        assertTrue("shoud be taken", result);

    }

}
