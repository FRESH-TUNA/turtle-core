package com.remember.core.voMakers.user;

import com.remember.core.domains.Question;
import com.remember.core.tools.LinkBuilder;
import com.remember.core.vos.user.UserQuestionVO;
import com.remember.core.vos.user.question.UserQuestionPlatformVO;
import com.remember.core.vos.user.question.UserQuestionPracticeStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserQuestionVoMaker {
    @Autowired
    private LinkBuilder builder;
    private final String SERVICE = "memo";
    private final String RESOURCES = "questions";

    private final String PLATFORM_RESOURCES = "platforms";
    private final String PRACTICE_STATUS_RESOURCES = "practiceStatusus";

    public UserQuestionVO toModel(
            String baseUrl,
            Question question) {

        UserQuestionVO vo = new UserQuestionVO(question);

        vo.add(builder.getServiceDetailLink(
                baseUrl,
                SERVICE,
                RESOURCES,
                question.getId()).withSelfRel());

        platformAssemble(baseUrl, vo.getPlatform());
        practiceStatusAssemble(baseUrl, vo.getPracticeStatus());

        return vo;
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
