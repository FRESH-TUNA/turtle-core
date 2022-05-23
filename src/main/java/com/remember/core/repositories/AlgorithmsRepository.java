package com.remember.core.repositories;

import com.remember.core.domains.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmsRepository extends JpaRepository<Algorithm, Long> {
}
