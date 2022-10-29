package com.remember.core.utils;

import com.remember.core.authentication.dtos.CentralAuthenticatedUser;
import com.remember.core.domains.UserIdentityField;
import com.remember.core.exceptions.ErrorCode;

import com.remember.core.exceptions.RememberException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 * facade 패턴 연습
 */
@Component
public class AuthenticatedFacadeImpl implements AuthenticatedFacade {

    @Override
    public CentralAuthenticatedUser getUserDetails() {
        Authentication authentication = getAuthentication();

        checkIsAuthenticated(authentication);

        return userDetailsExtract(authentication);
    }

    @Override
    public void checkIsAuthenticated(Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new RememberException(ErrorCode.NOT_LOGINED);
    }

    /*
     * helpers
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private CentralAuthenticatedUser userDetailsExtract(Authentication authentication) {
        return (CentralAuthenticatedUser) authentication.getPrincipal();
    }
}
