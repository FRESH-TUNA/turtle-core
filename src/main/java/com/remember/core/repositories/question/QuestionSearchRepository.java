package com.remember.core.repositories.question;

import com.querydsl.core.types.Predicate;
import com.remember.core.domains.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionSearchRepository<T, ID, USERID> {
    Page<Question> findAll(Pageable pageable, USERID user, Predicate predicate);

    Page<T> findAll(Pageable pageable, USERID user);

    Optional<T> findById(ID id);

    Optional<USERID> findUserOfQuestionById(ID id);
}
