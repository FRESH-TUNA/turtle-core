package com.remember.core.validatiors;

import com.remember.core.exceptions.RememberAuthenticationException;
import com.remember.core.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class AuthValidatior {
    public void signUppasswordEqual(String password1, String password2) {
        if(!password1.equals(password2))
            throw new RememberAuthenticationException(ErrorCode.PASSWORD_CONFORM_FAILED);
    }
}
