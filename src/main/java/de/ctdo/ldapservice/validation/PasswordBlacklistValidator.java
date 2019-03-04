package de.ctdo.ldapservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PasswordBlacklistValidator implements ConstraintValidator<PasswordBlacklist, String> {

    // hilft nicht, oder? :)
    private final Collection<String> words = new ArrayList<>(Arrays.asList(new String[]{
        "foobar", "foobar23", "foobar42", "foobar2342", "test", "testtest",
        "hallo", "hallo", "hallo", "password","passwort",
        "chaostreff", "ctdo", "ctdo2342"
    }));

    @Override
    public void initialize(final PasswordBlacklist constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return !words.contains(value.toLowerCase());
    }
}