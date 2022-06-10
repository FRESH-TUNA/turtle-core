package com.remember.core.assemblers;

import com.remember.core.domains.Algorithm;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.AlgorithmResponseDto;

import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AlgorithmsAssembler implements RepresentationModelAssembler<Algorithm, AlgorithmResponseDto> {
    private final ServerContext serverContext;
    private final String resources = "algorithms";

    @Override
    public AlgorithmResponseDto toModel(Algorithm entity) {
        return addSelfLink(new AlgorithmResponseDto(entity), entity.getId());
    }

    public AlgorithmResponseDto addSelfLink(AlgorithmResponseDto dto, Long id) {
        dto.add(LinkBuilder.getDetailLink(serverContext.getRoot(), resources, id).withSelfRel());
        return dto;
    }
}
