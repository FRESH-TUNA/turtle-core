package com.remember.core.repositories;

import com.remember.core.domains.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "algorithms", path = "algorithms")
public interface AlgorithmsRepository extends JpaRepository<Algorithm, Long> {
}
