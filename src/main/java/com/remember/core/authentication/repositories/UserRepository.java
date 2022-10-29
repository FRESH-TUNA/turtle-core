package com.remember.core.authentication.repositories;

import com.remember.core.authentication.domains.ProviderType;
import com.remember.core.authentication.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);

    Optional<User> findByUsernameAndProviderType(String username, ProviderType providerType);

    Optional<User> findById(Long id);
}
