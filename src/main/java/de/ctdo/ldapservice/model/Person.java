package de.ctdo.ldapservice.model;

import de.ctdo.ldapservice.validation.FieldMatch;
import de.ctdo.ldapservice.validation.PasswordComplex;
import de.ctdo.ldapservice.validation.UniqueEmail;
import de.ctdo.ldapservice.validation.UniqueUid;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import sun.security.util.Password;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "passwordConfirmation", message = "{constraints.fieldmatch.password}")
public class Person {

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z-_0-9öÖäÄüÜß]*")
    private String firstName;
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z-_0-9öÖäÄüÜß]*")
    private String lastName;

    @Email
    @NotEmpty
    @UniqueEmail
    private String emailAddress;

    private String groupId;

    @NotEmpty
    @UniqueUid
    @Pattern(regexp = "[a-zA-Z-_0-9]*", message = "{constraints.invaliduid}")
    private String uid;

    private String uidNumber;
    private String passwordSSHA;

    @Size(min = 6, max = 30)
    @PasswordComplex
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

    public String getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(String uidNumber) {
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


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", groupId='" + groupId + '\'' +
                ", uid='" + uid + '\'' +
                ", uidNumber='" + uidNumber + '\'' +
                ", passwordSSHA='" + passwordSSHA + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirmation='" + passwordConfirmation + '\'' +
                "} " + super.toString();
    }
}
