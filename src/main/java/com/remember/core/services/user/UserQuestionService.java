package com.remember.core.services.user;

import com.remember.core.assemblers.user.UserQuestionsAssembler;
import com.remember.core.domains.Question;
import com.remember.core.repositories.question.QuestionRepository;
import com.remember.core.vos.user.UserQuestionsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQuestionService {
    private final QuestionRepository repository;
    private final UserQuestionsAssembler listAssembler;
    private final PagedResourcesAssembler<Question> pageAssembler;

    public PagedModel<UserQuestionsVO> findAll(Pageable pageable, Long user_id) {
        return pageAssembler.toModel(repository.findAll(pageable, user_id), listAssembler);
    }
}
