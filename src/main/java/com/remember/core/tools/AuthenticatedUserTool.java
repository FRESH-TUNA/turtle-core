package com.remember.core.tools;

import com.remember.core.security.userDetails.BasicUserDetails;
import com.remember.core.security.userDetails.OauthUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticatedUserTool {
    public Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이후 이용하세요");

        Object principal = auth.getPrincipal();

        if(principal instanceof BasicUserDetails)
            return ((BasicUserDetails) auth.getPrincipal()).getId();
        else
            return ((OauthUserDetails) auth.getPrincipal()).getId();
    }
}

