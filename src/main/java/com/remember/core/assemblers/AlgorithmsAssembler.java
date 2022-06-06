package com.remember.core.assemblers;

import com.remember.core.domains.Algorithm;
import com.remember.core.utils.linkBuilders.LinkBuilder;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.AlgorithmResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AlgorithmsAssembler implements RepresentationModelAssembler<Algorithm, AlgorithmResponseDto> {
    private final LinkBuilder builder;
    private final ServerContext serverContext;
    private final String resources = "algorithms";

    @Override
    public AlgorithmResponseDto toModel(Algorithm entity) {
        AlgorithmResponseDto algorithm = new AlgorithmResponseDto(entity);
        String base = serverContext.getRoot();
        algorithm.add(builder.getDetailLink(base, resources, algorithm.getId()).withSelfRel());
        return algorithm;
    }
}
