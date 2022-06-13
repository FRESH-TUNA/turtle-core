package com.remember.core.assemblers;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.responses.PracticeStatusResponse;
import com.remember.core.utils.ServerContext;
import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PracticeStatusAssembler implements RepresentationModelAssembler<PracticeStatus, PracticeStatusResponse> {
    private final ServerContext serverContext;
    private final String resources = "practiceStatusus";

    @Override
    public PracticeStatusResponse toModel(PracticeStatus entity) {
        PracticeStatusResponse dto = new PracticeStatusResponse(entity);
        dto.add(LinkBuilder.getDetailLink(serverContext.getRoot(), resources, entity.name()).withSelfRel());
        return dto;
    }
}
