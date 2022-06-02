package com.remember.core.authService.ros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor // for ModelAttribute
//@NoArgsConstructor // for requestbody processing jackson
@Getter
public class UserRO {
    private String password;
    private String matchingPassword;
    private String username;
    private String nickname;
    private List<String> roles;
}

