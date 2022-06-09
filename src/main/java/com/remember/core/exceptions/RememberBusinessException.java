package com.remember.core.exceptions;

import lombok.Getter;

@Getter
public class RememberBusinessException extends RuntimeException{
    private final ErrorCode errorCode;

    public RememberBusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
