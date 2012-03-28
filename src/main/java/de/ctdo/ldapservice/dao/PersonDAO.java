package de.ctdo.ldapservice.dao;


import de.ctdo.ldapservice.model.Person;

import java.util.List;


public interface PersonDAO {

    List getAllNames();
    List<Person> getAll();

    void create(Person p);
    int getNextFreeGroupId();
    int getNextFreeUserId();

}
