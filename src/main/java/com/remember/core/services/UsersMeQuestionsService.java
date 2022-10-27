package com.remember.core.services;

import com.remember.core.domainFactories.QuestionFactory;
import com.remember.core.domains.Question;
import com.remember.core.domains.UserIdentityField;
import com.remember.core.dtos.responses.AlgorithmResponse;
import com.remember.core.dtos.responses.PlatformResponse;
import com.remember.core.dtos.responses.PracticeStatusResponse;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.exceptions.RememberException;

import com.remember.core.repositories.question.QuestionRepository;
import com.remember.core.dtos.requests.QuestionRequest;
import com.remember.core.searchParams.QuestionParams;
import com.remember.core.controllers.hateoasProcessors.QuestionHateoasProcessor;
import com.remember.core.controllers.hateoasProcessors.QuestionListAssembler;

import com.remember.core.dtos.responses.question.QuestionResponse;
import com.remember.core.dtos.responses.question.QuestionListResponse;
import com.remember.core.utils.AuthenticatedFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

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

    private final QuestionFactory factory;
    private final QuestionListAssembler listAssembler;
    private final QuestionHateoasProcessor assembler;
    private final PagedResourcesAssembler<Question> pageAssembler;

    private final AuthenticatedFacade authenticatedFacade;

    public Page<QuestionListResponse> findAll(Pageable pageable, QuestionParams params) {
        UserIdentityField user = authenticatedFacade.toUserIdentityField();

        return repository.findAll(pageable, user, params).map(q -> {
            PracticeStatusResponse practiceStatus = PracticeStatusResponse.of(q.getPracticeStatus());
            return QuestionListResponse.of(q, practiceStatus);
        });
    }

    public QuestionResponse findById(Long id) {
        Question question = getById(id);

        authenticatedFacade.checkResourceOwner(getUserOfQuestion(question));

        return toQuestionResponse(question);
    }

    @Transactional
    public QuestionResponse create(QuestionRequest ro) {
        UserIdentityField user = authenticatedFacade.toUserIdentityField();

        Question question = factory.toEntity(user, ro);

        return toQuestionResponse(repository.save(question));
    }

    @Transactional
    public QuestionResponse update(Long id, QuestionRequest ro) {
        Question question = getById(id);
        UserIdentityField user = getUserOfQuestion(question);

        authenticatedFacade.checkResourceOwner(user);
        Question updatedQuestion = factory.toEntity(user, id, ro);
        question = entityManager.merge(updatedQuestion);

        return toQuestionResponse(question);
    }

    @Transactional
    public QuestionResponse partial_update(Long id, QuestionRequest ro) {
        Question question = getById(id);
        UserIdentityField user = getUserOfQuestion(question);

        authenticatedFacade.checkResourceOwner(user);
        Question updatedQuestion = factory.toEntity(user, id, ro);
        question.partial_update(updatedQuestion);

        return assembler.toModel(question);
    }

    @Transactional
    public void delete(Long id) {
        UserIdentityField user = repository.findUserOfQuestionById(id)
                .orElseThrow(() -> new RememberException(ErrorCode.NOT_FOUND, "해당 문제를 찾을수 없습니다."));

        authenticatedFacade.checkResourceOwner(user);

        repository.deleteById(id);
    }

    /*
     * helpers
     */
    private Question getById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RememberException(ErrorCode.NOT_FOUND, "해당 문제를 찾을수 없습니다."));
    }

    private QuestionResponse toQuestionResponse(Question question) {
        List<AlgorithmResponse> algorithms = question.getAlgorithms()
                .stream().map(AlgorithmResponse::of).collect(Collectors.toList());
        PlatformResponse platform = PlatformResponse.of(question.getPlatform());
        PracticeStatusResponse practiceStatus = PracticeStatusResponse.of(question.getPracticeStatus());

        return QuestionResponse.of(question, platform, practiceStatus, algorithms);
    }

    private UserIdentityField getUserOfQuestion(Question question) {
        return question.getUser();
    }
}
