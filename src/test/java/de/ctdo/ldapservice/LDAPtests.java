package de.ctdo.ldapservice;

import de.ctdo.ldapservice.business.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertTrue;

@ContextConfiguration(locations = { "classpath:META-INF/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LDAPtests {

    @Autowired
    PersonService dao;

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
