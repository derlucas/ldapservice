package de.ctdo.ldapservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordBlacklistValidator.class)
public @interface PasswordBlacklist {

    String message() default "{constraints.passwordblacklist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
