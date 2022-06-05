package com.remember.core.AuthApp.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor // for ModelAttribute
//@NoArgsConstructor // for requestbody processing jackson
@Getter
public class UserRequestDto {
    private String password;
    private String matchingPassword;
    private String username;
    private String nickname;
    private List<String> roles;
}

