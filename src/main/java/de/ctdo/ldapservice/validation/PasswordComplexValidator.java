package de.ctdo.ldapservice.validation;

import de.ctdo.ldapservice.business.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PasswordComplexValidator implements ConstraintValidator<PasswordComplex, String> {

    // hilft nicht, oder? :)
    private final Collection<String> words = new ArrayList<String>(Arrays.asList(new String[]{"foobar",
        "foobar23",
        "foobar42",
        "foobar2342",
        "test",
        "hallo",
        "password",
        "passwort",
        "chaostreff",
        "ctdo"
    }));

    @Autowired
    private PersonService dao;

    @Override
    public void initialize(final PasswordComplex constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return (StringUtils.isNotBlank(value) && !words.contains(value) && StringUtils.length(value) > 7);
    }
}