package com.remember.core.assemblers;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.responses.PracticeStatusResponseDto;
import com.remember.core.utils.ServerContext;
import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PracticeStatususAssembler  implements RepresentationModelAssembler<PracticeStatus, PracticeStatusResponseDto> {
    private final ServerContext serverContext;
    private final String resources = "practiceStatusus";

    @Override
    public PracticeStatusResponseDto toModel(PracticeStatus entity) {
        PracticeStatusResponseDto dto = new PracticeStatusResponseDto(entity);
        dto.add(LinkBuilder.getDetailLink(serverContext.getRoot(), resources, entity.name()).withSelfRel());
        return dto;
    }
}
