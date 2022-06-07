package com.remember.core.AuthApp.exceptions;

import lombok.Getter;

@Getter
public class RememberException extends RuntimeException{
    private final ErrorCode errorCode;

    public RememberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
