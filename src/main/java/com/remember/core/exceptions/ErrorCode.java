package com.remember.core.exceptions;

/**
 * thanks to
 * https://github.com/woowacourse-teams/2020-6rinkers/blob/dev/back/cocktailpick-core/src/main/java/com/cocktailpick/core/common/exceptions/ErrorCode.java
 */
public enum ErrorCode {
    PASSWORD_CONFORM_FAILED(400, "A_001", "패스워드가 일치하지 않습니다");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
