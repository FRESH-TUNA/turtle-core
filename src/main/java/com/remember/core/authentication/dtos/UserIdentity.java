package com.remember.core.authentication.dtos;

import com.remember.core.authentication.domains.User;
import com.remember.core.domains.UserIdentityField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserIdentity {
    private Long id;

    @Override
    public boolean equals(Object other) {
        UserIdentity otherUserIdentity = (UserIdentity) other;
        return otherUserIdentity.id.equals(id);
    }

    /*
     * for authentication service
     */
    public static UserIdentity of(User user) {
        return new UserIdentity(user.getId());
    }

    /*
     * for bussiess logic service
     */
    public static UserIdentity of(UserIdentityField user) {
        return new UserIdentity(user.getUser());
    }
}
