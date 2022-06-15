package com.remember.core.assemblers;

import com.remember.core.domains.Platform;
import com.remember.core.responses.PlatformResponse;

import com.remember.core.utils.ServerContext;
import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlatformAssembler implements RepresentationModelAssembler<Platform, PlatformResponse> {
    private final ServerContext serverContext;
    private final String resources = "platforms";

    @Override
    public PlatformResponse toModel(Platform entity) {
        PlatformResponse platform = new PlatformResponse(entity);
        String base = serverContext.getRoot();
        platform.add(LinkBuilder.getDetailLink(base, resources, platform.getId()).withSelfRel());
        return platform;
    }
}
