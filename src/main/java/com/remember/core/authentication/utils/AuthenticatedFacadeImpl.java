package com.remember.core.authentication.utils;

import com.remember.core.authentication.dtos.RememberUserDetails;
import com.remember.core.domains.UserIdentityField;
import com.remember.core.exceptions.RememberAuthorizationException;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.exceptions.RememberAuthenticationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 * facade 패턴 연습
 */
@Component
public class AuthenticatedFacadeImpl implements AuthenticatedFacade {

    @Override
    public RememberUserDetails getUserDetails() {
        Authentication authentication = getAuthentication(getSecrityContext());

        checkIsAuthenticated(authentication);

        return userDetailsExtract(authentication);
    }

    @Override
    public UserIdentityField toUserIdentityField() {
        return generateUserIdentityField(getUserDetails());
    }

    @Override
    public void checkResourceOwner(UserIdentityField resourceUser) {
        checkResourceOwnerHelper(getUserDetails(), resourceUser);
    }

    /*
     * helpers
     */
    private SecurityContext getSecrityContext() {
        return SecurityContextHolder.getContext();
    }

    private Authentication getAuthentication(SecurityContext context) {
        return context.getAuthentication();
    }

    private RememberUserDetails userDetailsExtract(Authentication authentication) {
        return (RememberUserDetails) authentication.getPrincipal();
    }

    private UserIdentityField generateUserIdentityField(RememberUserDetails userDetails) {
        return userDetails.toUserIdentityField();
    }

    private void checkIsAuthenticated(Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new RememberAuthenticationException(ErrorCode.NOT_LOGINED);
    }

    private void checkResourceOwnerHelper(RememberUserDetails userDetails, UserIdentityField resourceUser) {
        if(!userDetails.isSameUser(resourceUser))
            throw new RememberAuthorizationException(ErrorCode.NOT_AUTHORIZED);
    }
}
