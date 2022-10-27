package com.remember.core.dtos.responses.datas;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestSuccessModelAttribute {
    private String message;
    private String link;

    public RequestSuccessModelAttribute(String message) {
        this.message = message;
    }
}
