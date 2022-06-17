package com.remember.core.controllers;

import com.remember.core.exceptions.ErrorCode;
import com.remember.core.exceptions.ErrorResponse;
import com.remember.core.requests.QuestionRequest;
import com.remember.core.responses.PracticeStatusResponse;
import com.remember.core.responses.RequestSuccessModelAttribute;
import com.remember.core.searchParams.QuestionParams;
import com.remember.core.services.AlgorithmsService;
import com.remember.core.services.PlatformsService;
import com.remember.core.services.PracticeStatususService;
import com.remember.core.services.UsersMeQuestionsService;

import com.remember.core.responses.AlgorithmResponse;
import com.remember.core.responses.PlatformResponse;
import com.remember.core.responses.question.QuestionResponse;
import com.remember.core.responses.question.QuestionListResponse;
import com.remember.core.utils.ServerContext;
import com.remember.core.utils.linkBuilders.LinkBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.hateoas.PagedModel;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 참고자료
 * https://bulnabang99.tistory.com/59
 * https://stackoverflow.com/questions/16394296/in-which-layer-should-validation-be-performed
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/me/questions")
public class UsersMeQuestionsController {
    private final UsersMeQuestionsService service;

    // for models
    private final PracticeStatususService practiceStatususService;
    private final PlatformsService platformsService;
    private final AlgorithmsService algorithmsService;
    private final ServerContext context;
    private final String ALGORITHMS = "algorithms";

    @GetMapping
    public String findAll(Pageable pageable,
                          @ModelAttribute QuestionParams params,
                          Model model,
                          @Nullable String error) {
        PagedModel<QuestionListResponse> questions = service.findAll(pageable, params);

        /*
         * modeling
         */
        model.addAttribute("questions", questions);
        model.addAttribute("platforms", platformsService.findAll().getContent());
        model.addAttribute("practiceStatusus", practiceStatususService.findAll().getContent());
        model.addAttribute("algorithms", algorithmsService.findAll().getContent());
        addErrorToModel(model, error);
        return "users/questions/list";
    }

    @GetMapping("/{id}")
    public String findById(Model model,
                           @PathVariable Long id) {
        QuestionResponse question = service.findById(id);

        /*
         * modeling
         */
        model.addAttribute("question", question);
        model.addAttribute("practiceStatusus", practiceStatususService.findAll());
        return "users/questions/detail";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView create(@ModelAttribute @Validated QuestionRequest ro,
                               RedirectAttributes attributes) {
        QuestionResponse question = service.create(ro);

        /*
         * redirects
         */
        RedirectView response = new RedirectView("/users/me/questions", true);
        RequestSuccessModelAttribute attribute = new RequestSuccessModelAttribute(
                "문제 생성에 성공했습니다.",
                question.getLink("self").get().getHref());
        attributes.addFlashAttribute("success", attribute);
        return response;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView update(@PathVariable Long id,
                               @ModelAttribute @Validated QuestionRequest ro,
                               RedirectAttributes attributes) {
        QuestionResponse question = service.update(id, ro);

        RedirectView response = new RedirectView(question.getLink("self").get().getHref(), true);
        RequestSuccessModelAttribute attribute = new RequestSuccessModelAttribute("문제 업데이트에 성공했습니다.");
        attributes.addFlashAttribute("success", attribute);
        return response;
    }

    @ResponseBody
    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public QuestionResponse partial_update(@PathVariable Long id, @RequestBody QuestionRequest ro) {
        return service.partial_update(id, ro);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView delete(@PathVariable Long id, RedirectAttributes attributes) {
        service.delete(id);

        RedirectView response = new RedirectView("/users/me/questions", true);
        RequestSuccessModelAttribute attribute = new RequestSuccessModelAttribute("문제를 삭제했습니다.");
        attributes.addFlashAttribute("success", attribute);
        return response;
    }

    /*
     * update form
     */
    @GetMapping("/{id}/forms/update")
    public String updateView(@PathVariable Long id, Model model) {
        QuestionResponse question = service.findById(id);
        CollectionModel<PlatformResponse> platforms = platformsService.findAll();
        CollectionModel<PracticeStatusResponse> practiceStatusus = practiceStatususService.findAll();
        CollectionModel<AlgorithmResponse> algorithms = algorithmsService.findAll();

        /*
         * modeling
         */
        Set<Long> curAlgorithms = question.getAlgorithms().stream().map(a -> a.getId()).collect(Collectors.toSet());
        model.addAttribute("curAlgorithms", curAlgorithms);
        model.addAttribute("algorithms", algorithms.getContent());
        model.addAttribute("platforms", platforms);
        model.addAttribute("practiceStatusus", practiceStatusus.getContent());
        model.addAttribute("question", question);
        return "users/questions/forms/update";
    }

    /*
     * helpers
     */
    private void addErrorToModel(Model model, String error) {
        if(!Objects.isNull(error))
            model.addAttribute("error", ErrorResponse.of(ErrorCode.valueOf(error)));
    }
}
