package com.remember.core.authentication.dtos;

import com.remember.core.domains.UserIdentityField;

/*
 * user를 구분해주는 역활을 담당하는 필드에 대한 기능을 추가해준다.
 */
public interface RememberUserDetailsSupport {
    UserIdentityField toUserIdentityField();

    boolean isSameUser(UserIdentityField user);
}
