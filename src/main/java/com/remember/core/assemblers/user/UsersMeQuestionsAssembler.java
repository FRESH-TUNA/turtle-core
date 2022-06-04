package com.remember.core.assemblers.user;

import com.remember.core.domains.Question;
import com.remember.core.tools.LinkBuilder;

import com.remember.core.tools.ServerContext;
import com.remember.core.responseDtos.question.QuestionsVO;
import com.remember.core.responseDtos.question.QuestionPracticeStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

@Component
public class UsersMeQuestionsAssembler implements RepresentationModelAssembler<Question, QuestionsVO> {
    @Autowired
    private LinkBuilder builder;

    @Autowired
    private ServerContext serverContext;

    private final String PARENT_RESOURCES = "users";
    private final String RESOURCES = "questions";
    private final String PRACTICE_STATUS_RESOURCES = "practiceStatusus";

    public QuestionsVO toModel(Question question) {
        QuestionsVO vo = new QuestionsVO(question);
        vo = addSelfLink(vo);
        vo = practiceStatusAssemble(vo);
        return vo;
    }

    /**
     * helpers
     */

    private QuestionsVO addSelfLink(QuestionsVO vo) {
        StringBuilder url = new StringBuilder();
        String root = serverContext.getRoot();

        url.append(root); url.append("/");
        url.append(PARENT_RESOURCES); url.append("/me/");
        url.append(RESOURCES); url.append("/"); url.append(vo.getId());

        vo.add(Link.of(url.toString()).withSelfRel());

        return vo;
    }

    private QuestionsVO practiceStatusAssemble(QuestionsVO vo) {
        String baseUrl = serverContext.getRoot();
        QuestionPracticeStatusVO ps = vo.getPracticeStatus();
        ps.add(builder.getDetailLink(baseUrl, PRACTICE_STATUS_RESOURCES, ps.getId()).withSelfRel());
        return vo;
    }
}
