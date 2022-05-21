package com.remember.core.repositories;

import com.remember.core.domains.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
}
