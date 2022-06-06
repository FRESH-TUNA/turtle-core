package com.remember.core.assemblers;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.utils.linkBuilders.LinkBuilder;
import com.remember.core.responses.PracticeStatusResponseDto;
import com.remember.core.utils.ServerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PracticeStatususAssembler  implements RepresentationModelAssembler<PracticeStatus, PracticeStatusResponseDto> {
    private final ServerContext serverContext;
    private final LinkBuilder builder;
    private final String resources = "practiceStatusus";

    public List<PracticeStatusResponseDto> assemble(String baseUrl, List<PracticeStatusResponseDto> practiceStatusus) {
        for (PracticeStatusResponseDto vo :practiceStatusus)
            vo.add(builder.getDetailLink(baseUrl, resources, vo.getId()).withSelfRel());
        return practiceStatusus;
    }

    @Override
    public PracticeStatusResponseDto toModel(PracticeStatus entity) {
        PracticeStatusResponseDto practiceStatusResponseDto = new PracticeStatusResponseDto(entity);
        String base = serverContext.getRoot();
        practiceStatusResponseDto.add(builder.getDetailLink(base, resources, practiceStatusResponseDto.getId()).withSelfRel());
        return practiceStatusResponseDto;
    }
}
