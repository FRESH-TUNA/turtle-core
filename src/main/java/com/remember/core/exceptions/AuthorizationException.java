package com.remember.core.exceptions;

public class AuthorizationException extends RememberException{
    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
