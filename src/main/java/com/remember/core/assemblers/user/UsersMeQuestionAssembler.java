package com.remember.core.assemblers.user;

import com.remember.core.domains.Question;
import com.remember.core.utils.ServerContext;
import com.remember.core.responses.question.QuestionResponseDto;
import com.remember.core.responses.question.QuestionPlatformResponseDto;

import com.remember.core.utils.linkBuilders.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsersMeQuestionAssembler implements RepresentationModelAssembler<Question, QuestionResponseDto> {
    @Autowired
    private ServerContext serverContext;

    private final String RESOURCES = "users/me/questions";
    private final String PLATFORM_RESOURCES = "platforms";

    @Override
    public QuestionResponseDto toModel(Question question) {
        QuestionResponseDto vo = new QuestionResponseDto(question);
        vo = addSelfLink(vo);
        vo = platformAssemble(vo);
        return vo;
    }

    /**
     * helpers
     */
    private QuestionResponseDto addSelfLink(QuestionResponseDto vo) {
        StringBuilder url = new StringBuilder();
        String root = serverContext.getRoot();

        url.append(root); url.append("/");
        url.append(RESOURCES); url.append("/"); url.append(vo.getId());

        vo.add(Link.of(url.toString()).withSelfRel());

        return vo;
    }

    private QuestionResponseDto platformAssemble(QuestionResponseDto vo) {
        String baseUrl = serverContext.getRoot();
        QuestionPlatformResponseDto p = vo.getPlatform();
        p.add(LinkBuilder.getDetailLink(baseUrl, PLATFORM_RESOURCES, p.getId()).withSelfRel());
        return vo;
    }
}
