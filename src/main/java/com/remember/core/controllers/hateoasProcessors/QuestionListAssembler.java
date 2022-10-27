package com.remember.core.controllers.hateoasProcessors;

import com.remember.core.domains.Question;
import com.remember.core.dtos.responses.PracticeStatusResponse;
import com.remember.core.dtos.responses.question.QuestionListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionListAssembler implements RepresentationModelAssembler<Question, QuestionListResponse> {
    private final PracticeStatusAssembler practiceStatusAssembler;
    private final String RESOURCES = "users/me/questions";

    public QuestionListResponse toModel(Question question) {
        PracticeStatusResponse practiceStatus
                = practiceStatusAssembler.toModel(question.getPracticeStatus());

        QuestionListResponse vo = QuestionListResponse.of(question, practiceStatus);
        return vo.setSelfLink(RESOURCES, question.getId());
    }
}
