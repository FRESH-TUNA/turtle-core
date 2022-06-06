package com.remember.core.utils;

import com.remember.core.security.RememberUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService<Long> {
    @Override
    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이후 이용하세요");

        return (Long) ((RememberUserDetails) auth.getPrincipal()).getId();
    }
}
