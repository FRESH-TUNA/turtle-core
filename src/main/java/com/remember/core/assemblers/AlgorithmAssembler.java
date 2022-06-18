package com.remember.core.assemblers;

import com.remember.core.domains.Algorithm;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.AlgorithmResponse;

import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AlgorithmAssembler implements RepresentationModelAssembler<Algorithm, AlgorithmResponse> {
    private final ServerContext serverContext;
    private final String resources = "algorithms";

    @Override
    public AlgorithmResponse toModel(Algorithm entity) {
        return addSelfLink(new AlgorithmResponse(entity), entity.getId());
    }

    public AlgorithmResponse addSelfLink(AlgorithmResponse dto, Long id) {
        dto.add(LinkBuilder.getDetailLink(serverContext.getRoot(), resources, id).withSelfRel());
        return dto;
    }
}
