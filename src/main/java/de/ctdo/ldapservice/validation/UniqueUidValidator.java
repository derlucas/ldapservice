package de.ctdo.ldapservice.validation;

import de.ctdo.ldapservice.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUidValidator implements ConstraintValidator<UniqueUid, String>
{
    @Autowired
    private PersonDAO dao;

    @Override
    public void initialize(final UniqueUid constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return !dao.isUidTaken(String.valueOf(value));
    }

    
}