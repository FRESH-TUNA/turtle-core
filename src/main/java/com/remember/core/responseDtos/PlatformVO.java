package com.remember.core.responseDtos;

import com.remember.core.domains.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class PlatformVO extends RepresentationModel<PlatformVO> {
    private Long id;
    private String name;
    private String link;

    public PlatformVO(Platform platform) {
        this.id = platform.getId();
        this.name = platform.getName();
        this.link = platform.getLink();
    }
}
