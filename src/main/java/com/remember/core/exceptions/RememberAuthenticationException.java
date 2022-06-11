package com.remember.core.exceptions;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class RememberAuthenticationException extends AuthenticationException {
    private final ErrorCode errorCode;

    public RememberAuthenticationException(ErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }
}
