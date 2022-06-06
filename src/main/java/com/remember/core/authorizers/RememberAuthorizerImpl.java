package com.remember.core.authorizers;

import com.remember.core.exceptions.UnauthorizedException;
import com.remember.core.utils.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RememberAuthorizerImpl implements RememberAuthorizer<Long> {
    private final AuthenticatedUserService<Long> userTool;

    @Override
    public void checkCurrentUserIsOwner(Long userIdOfTarget) {
        if(!userTool.getUserId().equals(userIdOfTarget))
            throw new UnauthorizedException("권한이 없습니다.");
    }
}
