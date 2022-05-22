package com.remember.core.repositories;

import com.remember.core.domains.PracticeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeStatusRepository extends JpaRepository<PracticeStatus, Long> {
}
