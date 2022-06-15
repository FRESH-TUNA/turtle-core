package com.remember.core.utils;

import com.remember.core.authentication.dtos.RememberUserDetails;
import com.remember.core.domains.UserIdentityField;

public interface AuthenticatedFacade {
    public RememberUserDetails getUserDetails();

    UserIdentityField generateAndGetUserIdentityField();
}
