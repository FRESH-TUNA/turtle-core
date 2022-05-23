package com.remember.core.voMakers.user;

import com.remember.core.domains.Question;
import com.remember.core.vos.user.UserQuestionsVO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserQuestionsVoMaker implements
        RepresentationModelAssembler<Question, UserQuestionsVO> {

    @Override
    public UserQuestionsVO toModel(Question q) {
        return new UserQuestionsVO(q);
    }
}
