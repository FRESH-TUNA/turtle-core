package com.remember.core.repositories.question;

import com.remember.core.domains.Question;
import com.remember.core.domains.UserIdentityField;
import com.remember.core.searchParams.QuestionParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionSearchRepository<T, ID> {
    Page<Question> findAll(Pageable pageable, UserIdentityField user, QuestionParams params);

    Page<T> findAll(Pageable pageable, UserIdentityField user);

    Optional<T> findById(ID id);

    Optional<UserIdentityField> findUserOfQuestionById(ID id);
}
