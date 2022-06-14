package com.remember.core.utils;

import com.remember.core.AuthenticationApp.dtos.RememberUserDetails;

public interface AuthenticatedFacade {
    public Long getUserId();

    public RememberUserDetails getUserDetails();
}
