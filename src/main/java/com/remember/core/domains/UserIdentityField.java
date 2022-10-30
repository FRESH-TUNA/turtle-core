package com.remember.core.domains;

import com.remember.core.exceptions.ErrorCode;
import com.remember.core.exceptions.RememberException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdentityField {
    @Column(nullable = false)
    private Long user;

    public void checkIsOwner(UserIdentityField user) {
        if(!this.user.equals(user.getUser()))
            throw new RememberException(ErrorCode.NOT_AUTHORIZED);
    }
}
