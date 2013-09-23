package de.ctdo.ldapservice.dao;


import de.ctdo.ldapservice.model.Person;

import java.util.List;


public interface PersonDAO {

    int getNextFreeUserId();
    Person create(Person person);
//    void update(Person person);
//    void delete(Person person);
    boolean isEmailTaken(String email);
//    List findByName(String name);
//    List findAll();

    boolean isUidTaken(String uid);

}
