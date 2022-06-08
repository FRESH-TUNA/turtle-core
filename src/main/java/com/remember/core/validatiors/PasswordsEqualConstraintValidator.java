package com.remember.core.validatiors;

import com.remember.core.AuthenticationApp.dtos.UserRequestDto;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

@Component
public class PasswordsEqualConstraintValidator implements
        ConstraintValidator<PasswordsEqualConstraint, UserRequestDto> {

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(UserRequestDto user, ConstraintValidatorContext context) {
        if(user.getPassword().equals(user.getMatchingPassword()))
            return true;
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("패스워드가 일치하지 않습니다")
                .addConstraintViolation();

        return false;
    }
}