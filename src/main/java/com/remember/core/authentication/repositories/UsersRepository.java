package com.remember.core.authentication.repositories;

import com.remember.core.authentication.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);
}
