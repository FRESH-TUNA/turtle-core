package com.remember.core.assemblers;

import com.remember.core.tools.LinkBuilder;
import com.remember.core.vos.PlatformVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlatformsAssembler {
    @Autowired
    private LinkBuilder builder;

    private final String resources = "platforms";

    public List<PlatformVO> assemble(String baseUrl, List<PlatformVO> platforms) {
        for (PlatformVO vo :platforms)
            vo.add(builder.getDetailLink(baseUrl, resources, vo.getId()).withSelfRel());
        return platforms;
    }
}
