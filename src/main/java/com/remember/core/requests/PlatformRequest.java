package com.remember.core.requests;

import com.remember.core.domains.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // for requestbody processing jackson
@Getter
public class PlatformRequest {
    private String name;
    private String link;

    public Platform toEntity() {
        return Platform.builder().name(name).link(link).build();
    }
}
