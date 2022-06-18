package com.remember.core.repositories.question;

import com.querydsl.core.types.Predicate;
import com.remember.core.domains.Question;
import com.remember.core.domains.UserIdentityField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionSearchRepository<T, ID> {
    Page<Question> findAll(Pageable pageable, UserIdentityField user, Predicate predicate);

    Page<T> findAll(Pageable pageable, UserIdentityField user);

    Optional<T> findById(ID id);

    Optional<UserIdentityField> findUserOfQuestionById(ID id);
}
