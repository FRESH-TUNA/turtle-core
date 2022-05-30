package com.remember.core.repositories.question;

import com.remember.core.domains.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionSearchRepository<T, ID> {
    Page<T> findAll(Pageable pageable, ID user);

    Optional<T> findById(ID id);
}
