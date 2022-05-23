package com.remember.core.services.user;

import com.remember.core.voMakers.user.UserQuestionsVoMaker;
import com.remember.core.domainMakers.user.UserQuestionsDomainMaker;
import com.remember.core.domains.Question;
import com.remember.core.repositories.question.QuestionRepository;
import com.remember.core.ros.user.UserQuestionsRO;
import com.remember.core.vos.user.UserQuestionsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQuestionsService {
    private final QuestionRepository repository;
    private final UserQuestionsDomainMaker deassembler;
    private final UserQuestionsVoMaker listAssembler;
    private final PagedResourcesAssembler<Question> pageAssembler;

    public PagedModel<UserQuestionsVO> findAll(Long userId, Pageable pageable) {
        return pageAssembler.toModel(repository.findAll(pageable, userId), listAssembler);
    }

    @Transactional
    public void create(Long userId, UserQuestionsRO ro) {
        repository.save(deassembler.toEntity(userId, ro));
    }
}
