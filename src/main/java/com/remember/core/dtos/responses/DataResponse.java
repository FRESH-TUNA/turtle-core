package com.remember.core.dtos.responses;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.remember.core.dtos.responses.datas.ResponseStatusData;
import org.springframework.lang.Nullable;

public class DataResponse<T> extends BaseResponse {
    private T content;

    private DataResponse(ResponseStatusData _status, T content) {
        super(_status);
        this.content = content;
    }

    @Nullable
    @JsonUnwrapped
    public T getContent() {
        return content;
    }


    public static <T> DataResponse<T> of(T content) {
        return new DataResponse<>(ResponseStatusData.OK, content);
    }
}
