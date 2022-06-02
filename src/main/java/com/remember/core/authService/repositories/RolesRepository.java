package com.remember.core.authService.repositories;

import com.remember.core.authService.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
}
