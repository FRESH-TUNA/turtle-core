package com.remember.core.AuthenticationApp.dtos;

import com.remember.core.AuthenticationApp.domains.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getNickname();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
