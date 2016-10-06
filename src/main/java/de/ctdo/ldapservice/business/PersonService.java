package de.ctdo.ldapservice.business;

import de.ctdo.ldapservice.model.Person;

import java.util.Optional;

public interface PersonService {

    Optional<Person> create(Person person);
    boolean isEmailTaken(String email);
    boolean isUidTaken(String uid);

}
