package com.remember.core.assemblers.user;

import com.remember.core.domains.Question;

import com.remember.core.utils.ServerContext;
import com.remember.core.responses.question.QuestionListResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

@Component
public class UsersMeQuestionsAssembler implements RepresentationModelAssembler<Question, QuestionListResponseDto> {
    @Autowired
    private ServerContext serverContext;

    private final String PARENT_RESOURCES = "users";
    private final String RESOURCES = "questions";

    public QuestionListResponseDto toModel(Question question) {
        QuestionListResponseDto vo = new QuestionListResponseDto(question);
        vo = addSelfLink(vo);
        return vo;
    }

    /**
     * helpers
     */

    private QuestionListResponseDto addSelfLink(QuestionListResponseDto vo) {
        StringBuilder url = new StringBuilder();
        String root = serverContext.getRoot();

        url.append(root); url.append("/");
        url.append(PARENT_RESOURCES); url.append("/me/");
        url.append(RESOURCES); url.append("/"); url.append(vo.getId());

        vo.add(Link.of(url.toString()).withSelfRel());

        return vo;
    }
}
