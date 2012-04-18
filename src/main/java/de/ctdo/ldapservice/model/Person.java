package de.ctdo.ldapservice.model;


import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public class Person {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String emailAddress;
    @NotNull
    private String gender;
//    @Past
//    private DateTime birthDate;

    @Min(value = 1000)
    @NotNull
    private int groupId;

    @NotNull
    private String uid;

    @Min(value = 1000)
    @NotNull
    private int uidNumber;
    
    private String passwordSSHA;

    public String getPasswordSSHA() {
        return passwordSSHA;
    }

    public void setPasswordSSHA(String passwordSSHA) {
        this.passwordSSHA = passwordSSHA;
    }

    public int getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(int uidNumber) {
        this.uidNumber = uidNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public DateTime getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(DateTime birthDate) {
//        this.birthDate = birthDate;
//    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
