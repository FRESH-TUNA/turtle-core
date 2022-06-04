package com.remember.core.assemblers;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.tools.LinkBuilder;
import com.remember.core.responseDtos.PracticeStatusVO;
import com.remember.core.tools.ServerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PracticeStatususAssembler  implements RepresentationModelAssembler<PracticeStatus, PracticeStatusVO> {
    private final ServerContext serverContext;
    private final LinkBuilder builder;
    private final String resources = "practiceStatusus";

    public List<PracticeStatusVO> assemble(String baseUrl, List<PracticeStatusVO> practiceStatusus) {
        for (PracticeStatusVO vo :practiceStatusus)
            vo.add(builder.getDetailLink(baseUrl, resources, vo.getId()).withSelfRel());
        return practiceStatusus;
    }

    @Override
    public PracticeStatusVO toModel(PracticeStatus entity) {
        PracticeStatusVO practiceStatusVO = new PracticeStatusVO(entity);
        String base = serverContext.getRoot();
        practiceStatusVO.add(builder.getDetailLink(base, resources, practiceStatusVO.getId()).withSelfRel());
        return practiceStatusVO;
    }
}
