package de.ctdo.ldapservice;

import de.ctdo.ldapservice.business.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LDAPTests {

    @Autowired
    PersonService dao;

    @Test
    public void testIsEmailTaken() {
        boolean result = dao.isEmailTaken("punkt@ctdo.de");
        assertTrue("should be taken", result);
    }

    @Test
    public void testIsEmailTakenFree() {
        boolean result = dao.isEmailTaken("punkt2222@ctdo.de");
        assertFalse("should not be taken", result);
    }

    @Test
    public void testIsUIDTaken() {
        boolean result = dao.isUidTaken("lucas");
        assertTrue("shoud be taken", result);
    }

    @Test
    public void testIsUIDTakenFree() {
        boolean result = dao.isUidTaken("lucas2222");
        assertFalse("shoud not be taken", result);
    }

}
