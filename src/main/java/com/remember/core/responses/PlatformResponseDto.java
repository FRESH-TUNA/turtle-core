package com.remember.core.responses;

import com.remember.core.domains.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@NoArgsConstructor
@Relation(collectionRelation = "platforms")
public class PlatformResponseDto extends RepresentationModel<PlatformResponseDto> {
    private Long id;
    private String name;
    private String link;

    public PlatformResponseDto(Platform platform) {
        this.id = platform.getId();
        this.name = platform.getName();
        this.link = platform.getLink();
    }
}
