package com.remember.core.assemblers.user;

import com.remember.core.assemblers.PracticeStatususAssembler;
import com.remember.core.domains.Question;

import com.remember.core.responses.PracticeStatusResponseDto;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.question.QuestionListResponseDto;
import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersMeQuestionListAssembler implements RepresentationModelAssembler<Question, QuestionListResponseDto> {
    private final ServerContext serverContext;
    private final PracticeStatususAssembler practiceStatususAssembler;
    private final String RESOURCES = "users/me/questions";

    public QuestionListResponseDto toModel(Question question) {
        QuestionListResponseDto vo = new QuestionListResponseDto(question);

        PracticeStatusResponseDto practiceStatusResponseDto
                = practiceStatususAssembler.toModel(question.getPracticeStatus());

        vo = addSelfLink(vo, question.getId());
        vo.setPracticeStatus(practiceStatusResponseDto);

        return vo;
    }

    private QuestionListResponseDto addSelfLink(QuestionListResponseDto vo, Long id) {
        vo.add(LinkBuilder.getDetailLink(serverContext.getRoot(), RESOURCES, id).withSelfRel());
        return vo;
    }
}
