package com.remember.core.exceptions;

public class AuthorizationException extends RememberBusinessException {
    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
