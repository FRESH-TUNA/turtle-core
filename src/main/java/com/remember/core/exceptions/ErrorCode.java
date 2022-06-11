package com.remember.core.exceptions;

/**
 * thanks to
 * https://github.com/woowacourse-teams/2020-6rinkers/blob/dev/back/cocktailpick-core/src/main/java/com/cocktailpick/core/common/exceptions/ErrorCode.java
 */
public enum ErrorCode {
    /*
     * authentication
     */
    PASSWORD_CONFORM_FAILED(400, "A_001", "패스워드가 일치하지 않습니다"),
    SAME_EMAIL_EXSITED(400, "A_002", "같은 이메일을 가진 계정이 존재합니다"),
    NAME_OF_USER_NOTFOUND(400, "A_003", "이메일을 가진 계정이 없습니다"),
    OAUTH_PROVIDER_MISMATCH(400, "A_004", "이미 같은 이메일을 가진 계정을 가지고 있습니다. 회원가입했던 계정으로 다시시도해주세요"),
    NOT_LOGINED(400, "A_005", "로그인후 이용해주세요"),

    /*
     * authorization
     */
    NOT_AUTHORIZED(401, "A_005", "권한이 없습니다"),

    /*
     * FROM VALIDATION
     */
    REQUEST_VALIDATION_FAIL(400, "B_001", "요청 데이터 검증에 실패했습니다");

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
