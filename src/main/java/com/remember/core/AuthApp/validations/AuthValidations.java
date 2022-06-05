package com.remember.core.AuthApp.validations;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthValidations {
    public void signUppasswordEqual(String password1, String password2) {
        if(!password1.equals(password2))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다.");
    }
}
