package de.ctdo.ldapservice.validation;

import de.ctdo.ldapservice.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>
{
    @Autowired
    PersonDAO dao;

    @Override
    public void initialize(final UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        boolean isValid = !dao.isEmailPresent(String.valueOf(value));

        if (!isValid) {

        }

        return isValid;
    }

    
}