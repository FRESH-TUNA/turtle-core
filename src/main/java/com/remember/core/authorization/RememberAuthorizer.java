package com.remember.core.authorization;

import com.remember.core.authentication.dtos.RememberUserDetails;
import com.remember.core.authentication.dtos.UserIdentity;
import com.remember.core.domains.UserIdentityField;
import com.remember.core.exceptions.AuthorizationException;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.utils.AuthenticatedFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 참고자료
 * // https://velog.io/@dhk22/Spring-Security-%ED%86%A0%ED%81%B0%EB%B0%A9%EC%8B%9D-%EC%9D%B8%EC%A6%9D-JWT
 * // https://www.baeldung.com/spring-security-oauth-jwt
 * //https://bcp0109.tistory.com/303
 */

@Component
@RequiredArgsConstructor
public class RememberAuthorizer {
    private final AuthenticatedFacade authenticatedFacade;

    public void checkCurrentUserIsOwner(UserIdentityField userIdentityField) {
        RememberUserDetails userDetails = authenticatedFacade.getUserDetails();
        UserIdentity userIdentity = UserIdentity.of(userIdentityField);

        if(!userDetails.checkOwnerOfResource(userIdentity))
            throw new AuthorizationException(ErrorCode.NOT_AUTHORIZED);
    }
}
