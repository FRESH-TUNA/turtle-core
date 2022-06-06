package com.remember.core.exceptions;

import org.springframework.core.NestedRuntimeException;

public class UnauthorizedException extends NestedRuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}

