package de.ctdo.ldapservice.model;

import de.ctdo.ldapservice.validation.FieldMatch;
import de.ctdo.ldapservice.validation.PasswordBlacklist;
import de.ctdo.ldapservice.validation.UniqueEmail;
import de.ctdo.ldapservice.validation.UniqueUid;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
//@ToString(exclude = {"password", "passwordConfirmation", "passwordSSHA"})
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
    @Pattern(regexp = "[a-z-_0-9]*", message = "{constraints.invaliduid}")
    private String uid;

    private String uidNumber;
    private String passwordSSHA;

    @Size(min = 8, max = 40)
    @PasswordBlacklist
    private String password;

    private String passwordConfirmation;

    @NotEmpty
    private String loginShell = "/bin/bash";


}
