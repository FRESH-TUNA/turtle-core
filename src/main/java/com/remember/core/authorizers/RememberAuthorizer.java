package com.remember.core.authorizers;

public interface RememberAuthorizer<USERID> {
    public void checkCurrentUserIsOwner(USERID userIdOfTarget);
}
