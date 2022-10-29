package com.remember.core.authentication.repositories;

import com.remember.core.authentication.domains.RefreshToken;
import com.remember.core.authentication.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);
}
