package com.remember.core.controllers.user;

import com.remember.core.vos.user.UserQuestionsVO;
import com.remember.core.services.user.UserQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 참고자료
 * https://bulnabang99.tistory.com/59
 */
@Controller
@RequestMapping("/users")
public class UserQuestionController {
    @Autowired
    private UserQuestionService userQuestionService;

    /*
     * views
     */
    @GetMapping("/{user_id}/questions")
    public String findAll(
            @PathVariable Long user_id, Pageable pageable, Model model) {
        PagedModel<UserQuestionsVO> questions = userQuestionService.findAll(pageable, user_id);
        model.addAttribute("questions", questions);
        model.addAttribute("test", Integer.valueOf(1));
        return "user/question/list";
    }

    @GetMapping("/{user_id}/questions/{id}")
    public UserQuestionsVO findById(
            @PathVariable Long user_id, @PathVariable Long id) {
        return null;
    }
}
