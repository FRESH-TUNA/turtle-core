package com.remember.core.repositories;

import com.remember.core.domains.PracticeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "practiceStatuses", path = "practiceStatuses")
public interface PracticeStatususRepository extends JpaRepository<PracticeStatus, Long> {
}
