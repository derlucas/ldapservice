package de.ctdo.ldapservice.validation;

import de.ctdo.ldapservice.business.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private PersonService dao;

    @Override
    public void initialize(final UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return !dao.isEmailTaken(String.valueOf(value));
    }


}