package com.remember.core.requests;

import com.remember.core.domains.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // for requestbody processing jackson
@Getter
public class AlgorithmRequest {
    private String name;

    public Algorithm toEntity() {
        return Algorithm.builder().name(name).build();
    }
}
