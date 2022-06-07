package com.remember.core.AuthApp.exceptions;

public class AuthenticationException extends RememberException{
    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
