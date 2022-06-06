package com.remember.core.services.users;

import com.remember.core.authorizers.RememberAuthorizer;
import com.remember.core.domainMappers.QuestionDomainMapper;

import com.remember.core.domains.Question;
import com.remember.core.predicates.QuestionPredicate;

import com.remember.core.repositories.question.QuestionRepository;
import com.remember.core.requests.QuestionRequestDto;
import com.remember.core.searchParams.QuestionParams;
import com.remember.core.assemblers.user.UsersMeQuestionAssembler;
import com.remember.core.assemblers.user.UsersMeQuestionsAssembler;

import com.remember.core.responses.question.QuestionResponseDto;
import com.remember.core.responses.question.QuestionListResponseDto;
import com.remember.core.utils.AuthenticatedUserService;
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

    private final QuestionDomainMapper domainMapper;
    private final UsersMeQuestionsAssembler listAssembler;
    private final UsersMeQuestionAssembler serializer;
    private final PagedResourcesAssembler<Question> pageAssembler;

    private final AuthenticatedUserService<Long> userTool;
    private final RememberAuthorizer<Long> authorizer;

    public PagedModel<QuestionListResponseDto> findAll(Pageable pageable, QuestionParams params) {
        Page<Question> questions = repository.findAll(
                pageable,
                userTool.getUserId(),
                QuestionPredicate.generate(params)
        );

        return pageAssembler.toModel(questions, listAssembler);
    }

    public QuestionResponseDto findById(Long id) {
        Question question = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 문제가 없습니다"));

        authorizer.checkCurrentUserIsOwner(question.getUser());
        return serializer.toModel(question);
    }

    @Transactional
    public QuestionResponseDto create(QuestionRequestDto ro) {
        Question question = repository.save(domainMapper.toEntity(userTool.getUserId(), ro));
        question = repository.findById(question.getId()).get();
        return serializer.toModel(question);
    }

    @Transactional
    public QuestionResponseDto update(Long id, QuestionRequestDto ro) {
        Question question = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 문제가 없습니다"));

        authorizer.checkCurrentUserIsOwner(question.getUser());

        Question updatedQuestion = domainMapper.toEntity(userTool.getUserId(), id, ro);
        question = entityManager.merge(updatedQuestion);
        return serializer.toModel(question);
    }

    @Transactional
    public QuestionResponseDto partial_update(Long id, QuestionRequestDto ro) {
        Question question = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 문제가 없습니다"));

        authorizer.checkCurrentUserIsOwner(question.getUser());

        Question updatedQuestion = domainMapper.toEntity(userTool.getUserId(), id, ro);
        question.partial_update(updatedQuestion);
        return new QuestionResponseDto(question);
    }

    @Transactional
    public void delete(Long id) {
        authorizer.checkCurrentUserIsOwner(repository
                        .findUserOfQuestionById(id)
                        .orElseThrow(() -> new EntityNotFoundException("해당 문제가 없습니다")));
        repository.deleteById(id);
    }
}
