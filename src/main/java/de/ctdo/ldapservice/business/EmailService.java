package de.ctdo.ldapservice.business;

import de.ctdo.ldapservice.model.Person;

public interface EmailService {

    void sendEmail(Person person);


}
