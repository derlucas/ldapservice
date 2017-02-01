package de.ctdo.ldapservice.business;

import de.ctdo.ldapservice.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmailServiceImplTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmail() throws Exception {

        Person person = new Person();
        person.setFirstName("Frank");
        person.setLastName("Nord");
        person.setEmailAddress("email@ctdo.de");
        person.setGroupId("2000");
        person.setLoginShell("/bin/false");
        person.setPassword("asd");
        person.setUid("1223");

        emailService.sendEmail(person);

    }
}