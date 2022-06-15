package com.remember.core.utils;

import com.remember.core.authentication.dtos.RememberUserDetails;

public interface AuthenticatedFacade {
    public RememberUserDetails getUserDetails();
}
