package com.remember.core.responses;

import com.remember.core.domains.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@NoArgsConstructor
@Relation(collectionRelation = "platforms")
public class PlatformResponse extends RepresentationModel<PlatformResponse> {
    private Long id;
    private String name;
    private String link;

    public PlatformResponse(Platform platform) {
        this.id = platform.getId();
        this.name = platform.getName();
        this.link = platform.getLink();
    }
}
