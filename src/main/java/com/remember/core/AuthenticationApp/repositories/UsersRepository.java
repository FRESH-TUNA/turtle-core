package com.remember.core.AuthenticationApp.repositories;

import com.remember.core.AuthenticationApp.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    Optional<User> findByEmail(String name);
}
