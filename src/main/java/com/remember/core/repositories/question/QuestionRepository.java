package com.remember.core.repositories.question;

import com.remember.core.domains.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionSearchRepository {

}
