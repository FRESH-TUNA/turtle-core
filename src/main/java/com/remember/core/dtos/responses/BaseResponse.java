package com.remember.core.dtos.responses;

import com.remember.core.dtos.responses.datas.ResponseStatusData;
import com.remember.core.exceptions.ErrorCode;

public class BaseResponse {
    private ResponseStatusData status;

    protected BaseResponse(ResponseStatusData status) {
        this.status = status;
    }

    public static BaseResponse OK = new BaseResponse(ResponseStatusData.OK);

    public static BaseResponse INTERNAL_SERVER_ERROR = new BaseResponse(
            ResponseStatusData.ofErrorCode(ErrorCode.INTERNAL_SERVER_ERROR));

    public ResponseStatusData getStatus() {
        return status;
    }

    public static BaseResponse ofErrorCode(ErrorCode code) {
        return new BaseResponse(ResponseStatusData.ofErrorCode(code));
    }
}
