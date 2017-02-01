package de.ctdo.ldapservice.business;

import de.ctdo.ldapservice.configuration.EmailConfig;
import de.ctdo.ldapservice.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailConfig emailConfig;
    private final MailSender mailSender;

    @Override
    public void sendEmail(Person person) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailConfig.getTo());
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

}
