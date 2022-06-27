package com.remember.core.validators;

import com.remember.core.authentication.dtos.UserRequest;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordsEqualConstraintValidator implements
        ConstraintValidator<PasswordsEqualConstraint, UserRequest> {

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(UserRequest user, ConstraintValidatorContext context) {
        if(user.getPassword().equals(user.getMatchingPassword()))
            return true;
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("패스워드가 일치하지 않습니다")
                .addConstraintViolation();

        return false;
    }
}