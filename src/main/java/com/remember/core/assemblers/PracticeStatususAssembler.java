package com.remember.core.assemblers;

import com.remember.core.tools.LinkBuilder;
import com.remember.core.vos.PracticeStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PracticeStatususAssembler {
    @Autowired
    private LinkBuilder builder;

    private final String resources = "practiceStatusus";

    public List<PracticeStatusVO> assemble(String baseUrl, List<PracticeStatusVO> practiceStatusus) {
        for (PracticeStatusVO vo :practiceStatusus)
            vo.add(builder.getDetailLink(baseUrl, resources, vo.getId()).withSelfRel());
        return practiceStatusus;
    }
}
