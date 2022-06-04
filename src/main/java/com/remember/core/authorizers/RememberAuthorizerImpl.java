package com.remember.core.authorizers;

import com.remember.core.tools.AuthenticatedUserTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RememberAuthorizerImpl implements RememberAuthorizer<Long> {
    @Autowired
    private AuthenticatedUserTool userTool;

    @Override
    public void checkCurrentUserIsOwner(Long userIdOfTarget) {

        if(!userTool.getUserId().equals(userIdOfTarget))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용자의 리소스가 아닙니다.");
    }
}
