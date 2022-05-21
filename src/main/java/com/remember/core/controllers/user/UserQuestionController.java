package com.remember.core.controllers.user;

import com.remember.core.responseDtos.user.UserQuestionResponseDto;
import com.remember.core.services.user.UserQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserQuestionController {
    @Autowired
    private UserQuestionService userQuestionService;

    /*
     * views
     */
    @GetMapping("/{user_id}/questions")
    public PagedModel<UserQuestionResponseDto> findAll(
            @PathVariable Long user_id, Pageable pageable) {
        return userQuestionService.findAll(pageable, user_id);
    }
}
