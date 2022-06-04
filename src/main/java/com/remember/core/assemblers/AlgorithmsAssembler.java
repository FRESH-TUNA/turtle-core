package com.remember.core.assemblers;

import com.remember.core.domains.Algorithm;
import com.remember.core.tools.LinkBuilder;
import com.remember.core.tools.ServerContext;
import com.remember.core.responseDtos.AlgorithmVO;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AlgorithmsAssembler implements RepresentationModelAssembler<Algorithm, AlgorithmVO> {
    private final LinkBuilder builder;
    private final ServerContext serverContext;
    private final String resources = "algorithms";

    @Override
    public AlgorithmVO toModel(Algorithm entity) {
        AlgorithmVO algorithm = new AlgorithmVO(entity);
        String base = serverContext.getRoot();
        algorithm.add(builder.getDetailLink(base, resources, algorithm.getId()).withSelfRel());
        return algorithm;
    }
}
