package com.remember.core.services.users;


import com.remember.core.authorizers.RememberAuthorizer;
import com.remember.core.domainMappers.QuestionDomainMapper;

import com.remember.core.domains.Question;
import com.remember.core.predicates.QuestionsPredicate;

import com.remember.core.repositories.question.QuestionRepository;
import com.remember.core.requestDtos.QuestionsRO;
import com.remember.core.searchParams.users.QuestionsParams;
import com.remember.core.assemblers.user.UsersMeQuestionAssembler;
import com.remember.core.tools.AuthenticatedUserTool;
import com.remember.core.assemblers.user.UsersMeQuestionsAssembler;

import com.remember.core.responseDtos.question.QuestionVO;
import com.remember.core.responseDtos.question.QuestionsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

//https://stackoverflow.com/questions/35719797/is-using-magic-me-self-resource-identifiers-going-against-rest-principles
//https://stackoverflow.com/questions/36520372/designing-uri-for-current-logged-in-user-in-rest-applications

@Service
@RequiredArgsConstructor
public class UsersMeQuestionsService {
    private final QuestionRepository repository;
    private final EntityManager entityManager;

    private final QuestionDomainMapper domainMapper;
    private final UsersMeQuestionsAssembler listAssembler;
    private final UsersMeQuestionAssembler serializer;
    private final PagedResourcesAssembler<Question> pageAssembler;

    private final AuthenticatedUserTool userTool;
    private final RememberAuthorizer authorizer;

    public PagedModel<QuestionsVO> findAll(Pageable pageable, QuestionsParams params) {
        Page<Question> questions = repository.findAll(
                pageable,
                userTool.getUserId(),
                QuestionsPredicate.generate(params)
        );

        return pageAssembler.toModel(questions, listAssembler);
    }

    public QuestionVO findById(Long id) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        authorizer.checkCurrentUserIsOwner(question.getUser());
        return serializer.toModel(question);
    }

    @Transactional
    public QuestionVO create(QuestionsRO ro) {
        Question question = repository.save(domainMapper.toEntity(userTool.getUserId(), ro));
        question = repository.findById(question.getId()).get();
        return serializer.toModel(question);
    }

    @Transactional
    public QuestionVO update(Long id, QuestionsRO ro) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        authorizer.checkCurrentUserIsOwner(question.getUser());

        Question updatedQuestion = domainMapper.toEntity(userTool.getUserId(), id, ro);
        question = entityManager.merge(updatedQuestion);
        return serializer.toModel(question);
    }

    @Transactional
    public QuestionVO partial_update(Long id, QuestionsRO ro) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        authorizer.checkCurrentUserIsOwner(question.getUser());

        Question updatedQuestion = domainMapper.toEntity(userTool.getUserId(), id, ro);
        question.partial_update(updatedQuestion);
        return new QuestionVO(question);
    }

    @Transactional
    public void delete(Long id) {
        authorizer.checkCurrentUserIsOwner(repository
                        .findUserOfQuestionById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id)));
        repository.deleteById(id);
    }
}
