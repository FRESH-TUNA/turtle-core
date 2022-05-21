package com.remember.core.repositories.question;

import com.remember.core.domains.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionSearchRepository {
    Page<Question> findAll(Pageable pageable, Long user);
}
