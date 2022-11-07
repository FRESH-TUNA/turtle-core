package com.remember.core.dtos.responses.datas;

import com.remember.core.exceptions.ErrorCode;

public class ResponseStatusData {
    private String code;
    private String msg;

    protected ResponseStatusData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ResponseStatusData OK = new ResponseStatusData("200", "success");

    public static ResponseStatusData ofErrorCode(ErrorCode errorCode) {
        return new ResponseStatusData(errorCode.getCode(), errorCode.getMessage());
    }
}
