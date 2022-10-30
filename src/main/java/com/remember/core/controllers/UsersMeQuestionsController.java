package com.remember.core.controllers;


import com.remember.core.authentication.dtos.CentralAuthenticatedUser;
import com.remember.core.consts.Urls;
import com.remember.core.dtos.responses.DataResponse;
import com.remember.core.dtos.requests.QuestionRequest;
import com.remember.core.dtos.responses.PracticeStatusResponse;
import com.remember.core.dtos.responses.datas.RequestSuccessModelAttribute;
import com.remember.core.dtos.searchParams.QuestionParams;
import com.remember.core.services.UsersMeQuestionsService;

import com.remember.core.dtos.responses.question.QuestionResponse;
import com.remember.core.dtos.responses.question.QuestionListResponse;

import com.remember.core.utils.UrlFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;



/**
 * 참고자료
 * https://bulnabang99.tistory.com/59
 * https://stackoverflow.com/questions/16394296/in-which-layer-should-validation-be-performed
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class UsersMeQuestionsController extends AbstractController {
    private final UsersMeQuestionsService service;

    @GetMapping(Urls.USERS.ME.QUESTIONS.ROOT)
    public ResponseEntity<?> findAll(Pageable pageable,
                                  @ModelAttribute QuestionParams params,
                                  @AuthenticationPrincipal CentralAuthenticatedUser userDetails) {
        Page<QuestionListResponse> questions = service.findAll(pageable, params, userDetails);

        PagedModel<QuestionListResponse> page = findAllPageResponseHelper(questions);

        return ResponseEntity.ok(DataResponse.of(page));
    }

    @GetMapping(Urls.USERS.ME.QUESTIONS.ID)
    public ResponseEntity<?> findById(@AuthenticationPrincipal CentralAuthenticatedUser userDetails,
                                      @PathVariable Long id) {
        QuestionResponse question = service.findById(id, userDetails);
        question.add(Link.of(currentRequest()).withSelfRel());
        return ResponseEntity.ok(DataResponse.of(question));
    }

    @PostMapping(Urls.USERS.ME.QUESTIONS.ROOT)
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponse<?> create(@RequestBody @Validated QuestionRequest ro,
                                    @AuthenticationPrincipal CentralAuthenticatedUser userDetails) {
        QuestionResponse question = service.create(ro, userDetails);
        question.add(
                Link.of(UrlFacade.USERS_ME_QUESTIONS_ID(currentRoot(), question.getId())).withSelfRel()
        );
        return DataResponse.of(question);
    }

    @PutMapping(Urls.USERS.ME.QUESTIONS.ID)
    public ResponseEntity<?> update(@PathVariable Long id,
                               @RequestBody @Validated QuestionRequest ro,
                               @AuthenticationPrincipal CentralAuthenticatedUser userDetails) {
        QuestionResponse question = service.update(id, ro, userDetails);
        question.add(Link.of(UrlFacade.USERS_ME_QUESTIONS_ID(currentRoot(), id)).withSelfRel());

        return ResponseEntity.ok(DataResponse.of(question));
    }

    @ResponseBody
    @PatchMapping(Urls.USERS.ME.QUESTIONS.ID)
    public ResponseEntity<?> partial_update(@PathVariable Long id,
                                           @RequestBody QuestionRequest ro,
                                           @AuthenticationPrincipal CentralAuthenticatedUser userDetails) {
        QuestionResponse question = service.partial_update(id, ro, userDetails);
        question.add(Link.of(UrlFacade.USERS_ME_QUESTIONS_ID(currentRoot(), id)).withSelfRel());

        return ResponseEntity.ok(DataResponse.of(question));
    }

    @DeleteMapping(Urls.USERS.ME.QUESTIONS.ID)
    public ResponseEntity<?> delete(@PathVariable Long id,
                               @AuthenticationPrincipal CentralAuthenticatedUser userDetails) {
        service.delete(id, userDetails);

        return ResponseEntity.ok(DataResponse.OK);
    }

    /*
     * helpers
     */
    private PagedModel<QuestionListResponse> findAllPageResponseHelper(Page<QuestionListResponse> questions) {
        questions.forEach(q -> {
            q.add(Link.of(UrlFacade.USERS_ME_QUESTIONS_ID(currentRoot(), q.getId())).withSelfRel());
            PracticeStatusResponse p = q.getPracticeStatus();
            p.add(Link.of(UrlFacade.PRACTICESTATUSUS_NAME(currentRoot(), p.getId())).withSelfRel());
        });

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                questions.getSize(),
                questions.getNumber(),
                questions.getTotalElements(),
                questions.getTotalPages()
        );

        PagedModel page = PagedModel.of(questions.getContent(), pageMetadata);
        page.add(Link.of(currentRequest()).withSelfRel());

        return page;
    }
}
