package com.remember.core.dtos.responses;

import com.remember.core.dtos.responses.datas.ResponseStatusData;

public class BaseResponse {
    private ResponseStatusData status;

    protected BaseResponse(ResponseStatusData status) {
        this.status = status;
    }

    public static BaseResponse OK = new BaseResponse(ResponseStatusData.OK);

    public ResponseStatusData getStatus() {
        return status;
    }
}
