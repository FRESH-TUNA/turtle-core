package com.remember.core.exceptions;

import org.springframework.security.core.AuthenticationException;

public class RememberAuthenticationException extends AuthenticationException  {
    private final ErrorCode errorCode;

    public RememberAuthenticationException(ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
