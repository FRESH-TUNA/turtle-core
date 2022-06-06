package com.remember.core.assemblers;

import com.remember.core.domains.Platform;
import com.remember.core.utils.linkBuilders.LinkBuilder;
import com.remember.core.responses.PlatformResponseDto;

import com.remember.core.utils.ServerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlatformsAssembler implements RepresentationModelAssembler<Platform, PlatformResponseDto> {
    private final LinkBuilder builder;

    private final ServerContext serverContext;

    private final String resources = "platforms";

    public List<PlatformResponseDto> assemble(String baseUrl, List<PlatformResponseDto> platforms) {
        for (PlatformResponseDto vo :platforms)
            vo.add(builder.getDetailLink(baseUrl, resources, vo.getId()).withSelfRel());
        return platforms;
    }

    @Override
    public PlatformResponseDto toModel(Platform entity) {
        PlatformResponseDto platform = new PlatformResponseDto(entity);
        String base = serverContext.getRoot();
        platform.add(builder.getDetailLink(base, resources, platform.getId()).withSelfRel());
        return platform;
    }
}
