package de.ctdo.ldapservice.dao;


import de.ctdo.ldapservice.model.Person;

import java.util.List;


public interface PersonDAO {

    int getNextFreeGroupId();
    int getNextFreeUserId();
    void create(Person person);
    void update(Person person);
    void delete(Person person);
    Person findByPrimaryKey(String firstName, String lastName);
    boolean isEmailPresent(String email);
    List findByName(String name);
    List findAll();

}
