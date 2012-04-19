package de.ctdo.ldapservice.model;

import de.ctdo.ldapservice.validation.FieldMatch;
import de.ctdo.ldapservice.validation.UniqueEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "passwordConfirmation", message = "{constraints.fieldmatch.password}")
public class Person {
    public enum Gender { MALE, FEMALE }

    
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    private String emailAddress;

    @NotEmpty()
    private String gender;
//    @Past
//    private DateTime birthDate;


    private int groupId;

    @NotEmpty
    private String uid;

    private int uidNumber;
    private String passwordSSHA;

    @Min(5)
    private String password;
    private String passwordConfirmation;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

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
