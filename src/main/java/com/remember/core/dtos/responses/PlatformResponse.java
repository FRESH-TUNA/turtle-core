package com.remember.core.dtos.responses;

import com.remember.core.domains.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Getter
@NoArgsConstructor
public class PlatformResponse extends RepresentationModel<PlatformResponse> {
    private Long id;
    private String name;
    private String link;

    private PlatformResponse(Long id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public static PlatformResponse of(Platform platform) {
        return new PlatformResponse(platform.getId(), platform.getName(), platform.getLink());
    }
}
