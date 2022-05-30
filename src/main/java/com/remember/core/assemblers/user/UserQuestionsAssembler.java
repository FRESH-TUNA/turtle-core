package com.remember.core.assemblers.user;

import com.remember.core.tools.LinkBuilder;
import com.remember.core.tools.NestedLinkBuilder;
import com.remember.core.vos.user.UserQuestionVO;
import com.remember.core.vos.user.UserQuestionsVO;
import com.remember.core.vos.user.question.UserQuestionPlatformVO;

import com.remember.core.vos.user.question.UserQuestionPracticeStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

@Component
public class UserQuestionsAssembler {
    @Autowired
    private NestedLinkBuilder nestedLinkBuilder;

    @Autowired
    private LinkBuilder builder;

    private final String PARENT_RESOURCES = "users";
    private final String RESOURCES = "questions";

    private final String PLATFORM_RESOURCES = "platforms";
    private final String PRACTICE_STATUS_RESOURCES = "practiceStatusus";

    public PagedModel<UserQuestionsVO> assemble(
            String baseUrl,
            Long userId,
            PagedModel<UserQuestionsVO> page) {
        for (UserQuestionsVO vo : page.getContent()) {
            vo.add(nestedLinkBuilder.getDetailLink(
                    baseUrl,
                    PARENT_RESOURCES,
                    userId,
                    RESOURCES,
                    vo.getId()).withSelfRel());
            practiceStatusAssemble(baseUrl, vo.getPracticeStatus());
        }

        return page;
    }

    public UserQuestionVO assemble(
            String baseUrl,
            Long userId,
            UserQuestionVO question) {

        question.add(nestedLinkBuilder.getDetailLink(
                    baseUrl,
                    PARENT_RESOURCES,
                    userId,
                    RESOURCES,
                    question.getId()).withSelfRel());

        platformAssemble(baseUrl, question.getPlatform());
        practiceStatusAssemble(baseUrl, question.getPracticeStatus());
        return question;
    }

    /**
     * helpers
     */
    private void platformAssemble(String baseUrl, UserQuestionPlatformVO p) {
        p.add(builder.getDetailLink(baseUrl, PLATFORM_RESOURCES, p.getId()).withSelfRel());
    }

    private void practiceStatusAssemble(String baseUrl, UserQuestionPracticeStatusVO ps) {
        ps.add(builder.getDetailLink(baseUrl, PRACTICE_STATUS_RESOURCES, ps.getId()).withSelfRel());
    }
}
