package com.remember.core.exceptions;

public class RememberAuthorizationException extends RememberBusinessException {
    public RememberAuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
