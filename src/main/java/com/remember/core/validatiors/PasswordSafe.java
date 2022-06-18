package com.remember.core.validatiors;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordSafeValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface PasswordSafe {
    String message() default "패스워드가 약합니다!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
