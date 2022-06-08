package com.remember.core.exceptions;

public class AuthenticationException extends RememberException {
    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
