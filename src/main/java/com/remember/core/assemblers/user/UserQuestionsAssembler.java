package com.remember.core.assemblers.user;

import com.remember.core.controllers.user.UserQuestionController;
import com.remember.core.domains.Question;
import com.remember.core.vos.user.UserQuestionsVO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserQuestionsAssembler implements
        RepresentationModelAssembler<Question, UserQuestionsVO> {

    @Override
    public UserQuestionsVO toModel(Question q) {
        UserQuestionsVO res = new UserQuestionsVO(q);

        //add self link
        res.add(linkTo(methodOn(UserQuestionController.class)
                .findById(get_user_id(q), q.getId()))
                .withSelfRel());
        return res;
    }

    private Long get_user_id(Question q) {
        return q.getUser();
    }
}
