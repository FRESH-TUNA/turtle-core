package com.remember.core.utils;

import com.remember.core.authentication.dtos.CentralAuthenticatedUser;
import org.springframework.security.core.Authentication;

public interface AuthenticatedFacade {
    CentralAuthenticatedUser getUserDetails();

    void checkIsAuthenticated(Authentication authentication);
}
