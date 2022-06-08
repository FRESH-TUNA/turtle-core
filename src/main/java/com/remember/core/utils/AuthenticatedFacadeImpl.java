package com.remember.core.utils;

import com.remember.core.AuthenticationApp.dtos.RememberUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticatedFacadeImpl implements AuthenticatedFacade {
    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이후 이용하세요");

        return ((RememberUserDetails) auth.getPrincipal()).getId();
    }
}
