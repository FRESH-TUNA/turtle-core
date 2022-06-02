package com.remember.core.services.user;

import com.remember.core.searchParams.users.UsersQuestionsSearchParams;
import com.remember.core.tools.AuthenticatedUserTool;
import com.remember.core.voMakers.user.UserQuestionsVoMaker;
import com.remember.core.domainMakers.user.UserQuestionsDomainMaker;
import com.remember.core.domains.Question;
import com.remember.core.repositories.question.QuestionRepository;
import com.remember.core.ros.user.UserQuestionsRO;
import com.remember.core.vos.user.UserQuestionVO;
import com.remember.core.vos.user.UserQuestionsVO;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class UserQuestionsService {
    private final QuestionRepository repository;
    private final UserQuestionsDomainMaker deassembler;
    private final UserQuestionsVoMaker listAssembler;
    private final PagedResourcesAssembler<Question> pageAssembler;
    private final EntityManager entityManager;

    private final AuthenticatedUserTool userTool;

    public PagedModel<UserQuestionsVO> findAll(Pageable pageable, UsersQuestionsSearchParams params) {
        String baseUri = requestURL();
        Long user = userTool.getUserId();
        return null;
        //return pageAssembler.toModel(repository.findAll(pageable, user, params), listAssembler);
    }

    public UserQuestionVO findById(Long id) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        return new UserQuestionVO(question);
    }

    @Transactional
    public UserQuestionVO create(Long userId, UserQuestionsRO ro) {
        Question question = repository.save(deassembler.toEntity(userId, ro));
        Long new_id = question.getId();

        question = repository.findById(new_id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + new_id));

        return new UserQuestionVO(question);
    }

    @Transactional
    public UserQuestionVO update(Long userId, Long id, UserQuestionsRO ro) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        Question updatedQuestion = deassembler.toEntity(userId, id, ro);

        question = entityManager.merge(updatedQuestion);

        return new UserQuestionVO(question);
    }

    @Transactional
    public UserQuestionVO partial_update(Long userId, Long id, UserQuestionsRO ro) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        Question updatedQuestion = deassembler.toEntity(userId, id, ro);

        question.partial_update(updatedQuestion);
        System.out.println(updatedQuestion.getPracticeStatus().getStatus());
        System.out.println("-------");
        System.out.println(question.getPracticeStatus().getStatus());
        return new UserQuestionVO(question);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /*
     * helpers
     */
    private String requestURL() {
        return BasicLinkBuilder.linkToCurrentMapping().toString();
    }
}
