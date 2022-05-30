package com.remember.core.assemblers;

import com.remember.core.tools.LinkBuilder;
import com.remember.core.vos.AlgorithmVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlgorithmsAssembler {
    @Autowired
    private LinkBuilder builder;

    private final String resources = "algorithms";

    public List<AlgorithmVO> assemble(String baseUrl, List<AlgorithmVO> algorithms) {
        for (AlgorithmVO vo :algorithms)
            vo.add(builder.getDetailLink(baseUrl, resources, vo.getId()).withSelfRel());
        return algorithms;
    }
}
