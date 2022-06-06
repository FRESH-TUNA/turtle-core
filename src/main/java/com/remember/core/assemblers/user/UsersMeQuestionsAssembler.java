package com.remember.core.assemblers.user;

import com.remember.core.domains.Question;
import com.remember.core.utils.linkBuilders.LinkBuilder;

import com.remember.core.utils.ServerContext;
import com.remember.core.responseDtos.question.QuestionListResponseDto;
import com.remember.core.responseDtos.question.QuestionPracticeStatusResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

@Component
public class UsersMeQuestionsAssembler implements RepresentationModelAssembler<Question, QuestionListResponseDto> {
    @Autowired
    private LinkBuilder builder;

    @Autowired
    private ServerContext serverContext;

    private final String PARENT_RESOURCES = "users";
    private final String RESOURCES = "questions";
    private final String PRACTICE_STATUS_RESOURCES = "practiceStatusus";

    public QuestionListResponseDto toModel(Question question) {
        QuestionListResponseDto vo = new QuestionListResponseDto(question);
        vo = addSelfLink(vo);
        vo = practiceStatusAssemble(vo);
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

    private QuestionListResponseDto practiceStatusAssemble(QuestionListResponseDto vo) {
        String baseUrl = serverContext.getRoot();
        QuestionPracticeStatusResponseDto ps = vo.getPracticeStatus();
        ps.add(builder.getDetailLink(baseUrl, PRACTICE_STATUS_RESOURCES, ps.getId()).withSelfRel());
        return vo;
    }
}
