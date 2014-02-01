package de.ctdo.ldapservice.business;


import de.ctdo.ldapservice.model.Person;


public interface PersonService {

    Person create(Person person);
    boolean isEmailTaken(String email);
    boolean isUidTaken(String uid);

}
