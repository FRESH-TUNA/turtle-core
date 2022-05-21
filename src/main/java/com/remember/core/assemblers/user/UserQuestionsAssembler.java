package com.remember.core.assemblers.user;


import com.remember.core.domains.Question;
import com.remember.core.responseDtos.user.UserQuestionResponseDto;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserQuestionsAssembler implements
        RepresentationModelAssembler<Question, UserQuestionResponseDto> {

    @Override
    public UserQuestionResponseDto toModel(Question q) {
        UserQuestionResponseDto res = new UserQuestionResponseDto(q);

        //add self link
        //res.add(linkTo(methodOn(U.class).findById(q.getId())).withSelfRel());
        return res;
    }
}