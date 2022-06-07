package com.remember.core.services.users;

import com.remember.core.searchParams.QuestionParams;
import com.remember.core.assemblers.user.UsersMeQuestionsAssembler;
import com.remember.core.domainFactories.QuestionFactory;
import com.remember.core.domains.Question;
import com.remember.core.repositories.question.QuestionRepository;
import com.remember.core.requests.QuestionRequestDto;
import com.remember.core.responses.question.QuestionResponseDto;
import com.remember.core.responses.question.QuestionListResponseDto;
import com.remember.core.utils.AuthenticatedFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class QuestionsService {
    private final QuestionRepository repository;
    private final QuestionFactory deassembler;
    private final UsersMeQuestionsAssembler listAssembler;
    private final PagedResourcesAssembler<Question> pageAssembler;
    private final EntityManager entityManager;

    private final AuthenticatedFacade userTool;

    public PagedModel<QuestionListResponseDto> findAll(Pageable pageable, QuestionParams params) {
        String baseUri = requestURL();


        //Long user = userTool.getUserId();
        return null;
        //return pageAssembler.toModel(repository.findAll(pageable, user, params), listAssembler);
    }

    public QuestionResponseDto findById(Long id) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        return new QuestionResponseDto(question);
    }

    @Transactional
    public QuestionResponseDto create(Long userId, QuestionRequestDto ro) {
        Question question = repository.save(deassembler.toEntity(userId, ro));
        Long new_id = question.getId();

        question = repository.findById(new_id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + new_id));

        return new QuestionResponseDto(question);
    }

    @Transactional
    public QuestionResponseDto update(Long userId, Long id, QuestionRequestDto ro) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        Question updatedQuestion = deassembler.toEntity(userId, id, ro);

        question = entityManager.merge(updatedQuestion);

        return new QuestionResponseDto(question);
    }

    @Transactional
    public QuestionResponseDto partial_update(Long userId, Long id, QuestionRequestDto ro) {
        Question question = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        Question updatedQuestion = deassembler.toEntity(userId, id, ro);

        question.partial_update(updatedQuestion);
        System.out.println(updatedQuestion.getPracticeStatus().getStatus());
        System.out.println("-------");
        System.out.println(question.getPracticeStatus().getStatus());
        return new QuestionResponseDto(question);
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
