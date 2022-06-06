package com.remember.core.authorizers;

import com.remember.core.exceptions.UnauthorizedException;

public interface RememberAuthorizer<USERID> {
    public void checkCurrentUserIsOwner(USERID userIdOfTarget) throws UnauthorizedException;
}
