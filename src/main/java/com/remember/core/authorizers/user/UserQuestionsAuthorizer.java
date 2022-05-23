package com.remember.core.authorizers.user;

import com.remember.core.security.RememberUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserQuestionsAuthorizer {
    public boolean check(Long userId, Authentication authentication) {
        Object userDetails = authentication.getPrincipal();
        if (userDetails instanceof RememberUserDetails) {
           return ((RememberUserDetails)userDetails).getId().equals(userId);
        }
        return false;
    }
}
