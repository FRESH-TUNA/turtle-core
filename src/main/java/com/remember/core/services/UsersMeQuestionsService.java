package com.remember.core.services;

import com.remember.core.authorizers.RememberAuthorizer;
import com.remember.core.domainFactories.QuestionFactory;

import com.remember.core.domains.Question;
import com.remember.core.predicateFactories.QuestionPredicateFactory;

import com.remember.core.repositories.question.QuestionRepository;
import com.remember.core.requests.QuestionRequest;
import com.remember.core.searchParams.QuestionParams;
import com.remember.core.assemblers.QuestionAssembler;
import com.remember.core.assemblers.QuestionListAssembler;

import com.remember.core.responses.question.QuestionResponse;
import com.remember.core.responses.question.QuestionListResponse;
import com.remember.core.utils.AuthenticatedFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

/**
 * 참고자료
 * https://stackoverflow.com/questions/35719797/is-using-magic-me-self-resource-identifiers-going-against-rest-principles
 * https://stackoverflow.com/questions/36520372/designing-uri-for-current-logged-in-user-in-rest-applications
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersMeQuestionsService {
    private final QuestionRepository repository;
    private final EntityManager entityManager;

    private final QuestionFactory domainMapper;
    private final QuestionListAssembler listAssembler;
    private final QuestionAssembler assembler;
    private final PagedResourcesAssembler<Question> pageAssembler;

    private final AuthenticatedFacade authenticatedFacade;
    private final RememberAuthorizer authorizer;

    public PagedModel<QuestionListResponse> findAll(Pageable pageable, QuestionParams params) {
        Page<Question> questions = repository.findAll(
                pageable,
                authenticatedFacade.getUserId(),
                QuestionPredicateFactory.generate(params)
        );

        return pageAssembler.toModel(questions, listAssembler);
    }

    public QuestionResponse findById(Long id) {
        Question question = getById(id);
        authorizer.checkCurrentUserIDIsOwner(question.getUser());
        return assembler.toModel(question);
    }

    @Transactional
    public QuestionResponse create(QuestionRequest ro) {
        Question question = repository.save(domainMapper.toEntity(authenticatedFacade.getUserId(), ro));
        return assembler.toModel(getById(question.getId()));
    }

    @Transactional
    public QuestionResponse update(Long id, QuestionRequest ro) {
        Question question = getById(id);

        authorizer.checkCurrentUserIDIsOwner(question.getUser());

        Question updatedQuestion = domainMapper.toEntity(authenticatedFacade.getUserId(), id, ro);
        question = entityManager.merge(updatedQuestion);
        return assembler.toModel(question);
    }

    @Transactional
    public QuestionResponse partial_update(Long id, QuestionRequest ro) {
        Question question = getById(id);

        authorizer.checkCurrentUserIDIsOwner(question.getUser());

        Question updatedQuestion = domainMapper.toEntity(authenticatedFacade.getUserId(), id, ro);
        question.partial_update(updatedQuestion);
        return assembler.toModel(question);
    }

    @Transactional
    public void delete(Long id) {
        authorizer.checkCurrentUserIDIsOwner(
                repository.findUserOfQuestionById(id).orElseThrow(() ->
                        new EntityNotFoundException("해당 문제가 없습니다")));
        repository.deleteById(id);
    }

    /*
     * helpers
     */
    private Question getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 문제가 없습니다"));
    }
}
