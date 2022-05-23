package com.remember.core.controllers.user;

import com.remember.core.assemblers.PlatformsAssembler;
import com.remember.core.assemblers.PracticeStatususAssembler;
import com.remember.core.assemblers.user.UserQuestionsAssembler;
import com.remember.core.ros.user.UserQuestionsRO;
import com.remember.core.services.PlatformsService;
import com.remember.core.services.PracticeStatususService;
import com.remember.core.tools.LinkBuilder;
import com.remember.core.tools.NestedLinkBuilder;
import com.remember.core.vos.PlatformVO;
import com.remember.core.vos.PracticeStatusVO;
import com.remember.core.vos.user.UserQuestionsVO;
import com.remember.core.services.user.UserQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.http.MediaType;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 참고자료
 * https://bulnabang99.tistory.com/59
 */
@Controller
@RequestMapping("/users")
@PreAuthorize("@userQuestionsAuthorizer.check(#userId, authentication)")
@RequiredArgsConstructor
public class UserQuestionsController {
    private final UserQuestionsService service;
    private final PracticeStatususService practiceStatususService;
    private final PlatformsService platformsService;

    private final UserQuestionsAssembler assembler;
    private final PlatformsAssembler platformsAssembler;
    private final PracticeStatususAssembler practiceStatususAssembler;

    private final NestedLinkBuilder nestedLinkBuilder;

    /*
     * SSR views
     */
    @GetMapping("/{userId}/questions")
    public String findAll(Pageable pageable, Model model, @PathVariable Long userId) {
        PagedModel<UserQuestionsVO> questions = service.findAll(userId, pageable);
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        questions = assembler.assemble(baseUri, userId, questions);
        model.addAttribute("questions", questions);
        return "user/question/list";
    }

    @PostMapping(value = "/{userId}/questions", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String create(@PathVariable Long userId, @ModelAttribute UserQuestionsRO ro) {
        service.create(userId, ro);

        return "redirect:" + nestedLinkBuilder
                .getListLink("users", userId, "questions")
                .getHref();
    }

    @GetMapping("/{userId}/questions/forms/create")
    public String createView(@PathVariable Long userId, Model model) {
        List<PlatformVO> platforms = platformsService.findAll();
        List<PracticeStatusVO> practiceStatusus = practiceStatususService.findAll();
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        platforms = platformsAssembler.assemble(baseUri, platforms);
        practiceStatusus = practiceStatususAssembler.assemble(baseUri, practiceStatusus);

        String create_link = nestedLinkBuilder
                .getListLink(baseUri, "users", userId, "questions")
                .getHref();

        model.addAttribute("create_link", create_link);
        model.addAttribute("platforms", platforms);
        model.addAttribute("practiceStatusus", practiceStatusus);
        return "user/question/form";
    }

    @GetMapping("/{userId}/questions/{id}")
    public UserQuestionsVO findById(
            @PathVariable Long userId, @PathVariable Long id) {
        return null;
    }
}
