package com.remember.core.controllers;

import com.remember.core.assemblers.AlgorithmsAssembler;
import com.remember.core.assemblers.PlatformsAssembler;
import com.remember.core.assemblers.PracticeStatususAssembler;
import com.remember.core.assemblers.user.UserQuestionsAssembler;
import com.remember.core.ros.user.UserQuestionsRO;
import com.remember.core.searchParams.users.UsersQuestionsSearchParams;
import com.remember.core.services.AlgorithmsService;
import com.remember.core.services.PlatformsService;
import com.remember.core.services.PracticeStatususService;

import com.remember.core.tools.NestedLinkBuilder;
import com.remember.core.vos.AlgorithmVO;
import com.remember.core.vos.PlatformVO;
import com.remember.core.vos.PracticeStatusVO;
import com.remember.core.vos.user.UserQuestionVO;
import com.remember.core.vos.user.UserQuestionsVO;
import com.remember.core.services.user.UserQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.http.MediaType;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 참고자료
 * https://bulnabang99.tistory.com/59
 */
@Controller
//@PreAuthorize("@userQuestionsAuthorizer.check(#userId, authentication)")
@RequiredArgsConstructor
public class QuestionsController {
    private final UserQuestionsService service;
    private final PracticeStatususService practiceStatususService;
    private final PlatformsService platformsService;
    private final AlgorithmsService algorithmsService;

    private final UserQuestionsAssembler assembler;
    private final PlatformsAssembler platformsAssembler;
    private final PracticeStatususAssembler practiceStatususAssembler;
    private final AlgorithmsAssembler algorithmsAssembler;

    private final NestedLinkBuilder nestedLinkBuilder;

    /*
     * SSR views
     */
    @GetMapping("/questions")
    public String findAll(Pageable pageable,
                          HttpServletRequest request,
                          @ModelAttribute UsersQuestionsSearchParams params,
                          Model model) {

        PagedModel<UserQuestionsVO> questions = service.findAll(pageable, params);
        List<PracticeStatusVO> practiceStatusus = practiceStatususService.findAll();
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

//        questions = assembler.assemble(baseUri, userId, questions);
        practiceStatusus = practiceStatususAssembler.assemble(baseUri, practiceStatusus);

        /*
         * modeling
         */
        model.addAttribute("search_status",
                params.getPracticeStatus() == null ? 0L : params.getPracticeStatus());
        model.addAttribute("search_input", params.getTitle() == null ? "" : params.getTitle());
        model.addAttribute("questions_url", request.getRequestURL());
        model.addAttribute("questions", questions);
        model.addAttribute("practiceStatusus", practiceStatusus);
        return "users/questions/list";
    }

    @GetMapping("/{userId}/questions/{id}")
    public String findById(
            Model model, @PathVariable Long userId, @PathVariable Long id) {
        UserQuestionVO question = service.findById(id);
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        question = assembler.assemble(baseUri, userId, question);

        model.addAttribute("question", question);
        return "users/questions/detail";
    }

    @PostMapping(value = "/{userId}/questions", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String create(@PathVariable Long userId, @ModelAttribute UserQuestionsRO ro) {
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        service.create(userId, ro);
        return "redirect:" + nestedLinkBuilder
                .getListLink(baseUri, "users", userId, "questions")
                .getHref();
    }

    @PutMapping(value = "/{userId}/questions/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String update(@PathVariable Long userId, @PathVariable Long id, @ModelAttribute UserQuestionsRO ro) {
        service.update(userId, id, ro);

        return "redirect:" + nestedLinkBuilder
                .getDetailLink("users", userId, "questions", id)
                .getHref();
    }

    @ResponseBody
    @PatchMapping(
            value = "/{userId}/questions/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public UserQuestionVO partial_update(@PathVariable Long userId, @PathVariable Long id, @RequestBody UserQuestionsRO ro) {
        System.out.println("----");
        System.out.println(ro.getPracticeStatus());
        return service.partial_update(userId, id, ro);
    }

    @DeleteMapping(value = "/{userId}/questions/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String delete(@PathVariable Long userId, @PathVariable Long id) {
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        service.delete(id);
        return "redirect:" + nestedLinkBuilder
                .getListLink(baseUri, "users", userId, "questions")
                .getHref();
    }

    @GetMapping("/{userId}/questions/forms/create")
    public String createView(@PathVariable Long userId, Model model) {
        List<PlatformVO> platforms = platformsService.findAll();
        List<PracticeStatusVO> practiceStatusus = practiceStatususService.findAll();
        List<AlgorithmVO> algorithms = algorithmsService.findAll();
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        platforms = platformsAssembler.assemble(baseUri, platforms);
        practiceStatusus = practiceStatususAssembler.assemble(baseUri, practiceStatusus);
        algorithms = algorithmsAssembler.assemble(baseUri, algorithms);

        String create_link = nestedLinkBuilder
                .getListLink(baseUri, "users", userId, "questions")
                .getHref();

        model.addAttribute("create_link", create_link);
        model.addAttribute("platforms", platforms);
        model.addAttribute("practiceStatusus", practiceStatusus);
        model.addAttribute("algorithms", algorithms);
        return "users/questions/forms/create";
    }

    @GetMapping("/{userId}/questions/{id}/forms/update")
    public String updateView(@PathVariable Long userId, @PathVariable Long id, Model model) {
        UserQuestionVO question = service.findById(id);
        List<PlatformVO> platforms = platformsService.findAll();
        List<PracticeStatusVO> practiceStatusus = practiceStatususService.findAll();
        List<AlgorithmVO> algorithms = algorithmsService.findAll();
        Set<Long> curAlgorithms = question.getAlgorithms().stream().map(a -> a.getId()).collect(Collectors.toSet());
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        question = assembler.assemble(baseUri, userId, question);
        platforms = platformsAssembler.assemble(baseUri, platforms);
        practiceStatusus = practiceStatususAssembler.assemble(baseUri, practiceStatusus);
        algorithms = algorithmsAssembler.assemble(baseUri, algorithms);

        model.addAttribute("algorithms", algorithms);
        model.addAttribute("curAlgorithms", curAlgorithms);
        model.addAttribute("platforms", platforms);
        model.addAttribute("practiceStatusus", practiceStatusus);
        model.addAttribute("question", question);
        return "users/questions/forms/update";
    }

    @GetMapping("/{userId}/questions/{id}/forms/delete")
    public String deleteView(@PathVariable Long userId, @PathVariable Long id, Model model) {
        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        String delete_link = nestedLinkBuilder
                .getDetailLink(baseUri, "users", userId, "questions", id)
                .getHref();

        model.addAttribute("delete_link", delete_link);
        return "users/questions/forms/delete";
    }
}
