package com.remember.core.dtos.responses;

import com.remember.core.dtos.responses.datas.ResponseStatusData;

public class BaseResponse {
    private ResponseStatusData _status;

    protected BaseResponse(ResponseStatusData _status) {
        this._status = _status;
    }

    public static BaseResponse OK = new BaseResponse(ResponseStatusData.OK);

    public ResponseStatusData get_status() {
        return _status;
    }
}
