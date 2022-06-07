package com.remember.core.AuthApp.validatiors;

import com.remember.core.AuthApp.exceptions.ErrorCode;
import com.remember.core.AuthApp.exceptions.RememberException;
import org.springframework.stereotype.Component;

@Component
public class AuthValidatior {
    public void signUppasswordEqual(String password1, String password2) {
        if(!password1.equals(password2))
            throw new RememberException(ErrorCode.PASSWORD_CONFORM_FAILED);
    }
}
