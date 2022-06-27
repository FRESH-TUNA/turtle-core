package com.remember.core.utils;

import com.remember.core.authentication.dtos.RememberUserDetails;
import com.remember.core.domains.UserIdentityField;

public interface AuthenticatedFacade {
    RememberUserDetails getUserDetails();

    UserIdentityField toUserIdentityField();

    void checkResourceOwner(UserIdentityField resourceUser);
}
