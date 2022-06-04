package com.remember.core.assemblers;

import com.remember.core.domains.Platform;
import com.remember.core.tools.LinkBuilder;
import com.remember.core.responseDtos.PlatformVO;

import com.remember.core.tools.ServerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlatformsAssembler implements RepresentationModelAssembler<Platform, PlatformVO> {
    private final LinkBuilder builder;

    private final ServerContext serverContext;

    private final String resources = "platforms";

    public List<PlatformVO> assemble(String baseUrl, List<PlatformVO> platforms) {
        for (PlatformVO vo :platforms)
            vo.add(builder.getDetailLink(baseUrl, resources, vo.getId()).withSelfRel());
        return platforms;
    }

    @Override
    public PlatformVO toModel(Platform entity) {
        PlatformVO platform = new PlatformVO(entity);
        String base = serverContext.getRoot();
        platform.add(builder.getDetailLink(base, resources, platform.getId()).withSelfRel());
        return platform;
    }
}
