package com.remember.core.authorizers;

import com.remember.core.exceptions.UnauthorizedException;
import com.remember.core.utils.AuthenticatedFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RememberAuthorizer {
    private final AuthenticatedFacade authenticatedFacade;

    public void checkCurrentUserIDIsOwner(Object userIdOfTarget) {
        if(!authenticatedFacade.getUserId().equals(userIdOfTarget))
            throw new UnauthorizedException("권한이 없습니다.");
    }
}
