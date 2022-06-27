package com.remember.core.assemblers;

import com.remember.core.domains.Question;
import com.remember.core.responses.PracticeStatusResponse;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.question.QuestionListResponse;
import com.remember.core.utils.linkBuilders.LinkBuilder;
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
