package com.remember.core.utils;

import com.remember.core.authentication.dtos.RememberUserDetails;
import com.remember.core.authentication.dtos.UserIdentity;
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
    public RememberUserDetails getUserDetails() {
        Authentication authentication = getAuthentication();

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

    @Override
    public void checkUserIsOwner(UserIdentity owner, UserIdentity user) {
        if(!owner.isSameUser(user))
            throw new RememberException(ErrorCode.NOT_AUTHORIZED);
    }

    /*
     * helpers
     */
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private RememberUserDetails userDetailsExtract(Authentication authentication) {
        return (RememberUserDetails) authentication.getPrincipal();
    }

    private UserIdentityField generateUserIdentityField(RememberUserDetails userDetails) {
        return userDetails.toUserIdentityField();
    }

    private void checkIsAuthenticated(Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken)
            throw new RememberException(ErrorCode.NOT_LOGINED);
    }

    private void checkResourceOwnerHelper(RememberUserDetails userDetails, UserIdentityField resourceUser) {
        if(!userDetails.isSameUser(resourceUser))
            throw new RememberException(ErrorCode.NOT_AUTHORIZED);
    }
}
