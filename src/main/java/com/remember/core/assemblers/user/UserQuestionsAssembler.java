package com.remember.core.assemblers.user;

import com.remember.core.tools.NestedLinkBuilder;
import com.remember.core.vos.user.UserQuestionsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

@Component
public class UserQuestionsAssembler {
    @Autowired
    private NestedLinkBuilder builder;

    public PagedModel<UserQuestionsVO> assemble(
            String baseUrl,
            Long userId,
            PagedModel<UserQuestionsVO> page) {
        for (UserQuestionsVO vo : page.getContent()) {
            vo.add(builder.getDetailLink(
                    baseUrl,
                    "users",
                    userId,
                    "questions",
                    vo.getId()).withSelfRel());
        }
        return page;
    }
}
