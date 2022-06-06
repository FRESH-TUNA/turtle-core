package com.remember.core.AuthApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 참고자료
 * https://sanghye.tistory.com/36
 */


@AllArgsConstructor // for ModelAttribute
//@NoArgsConstructor // for requestbody processing jackson
@Getter
public class UserRequestDto {
    @NotBlank(message="패스워드를 입력해주세요")
    private String password;

    @NotBlank(message="원하시는 페스워드를 다시 입력해주세요")
    private String matchingPassword;

    @Email(message="mail의 형식을 지켜주세요")
    private String username;

    private String nickname;
    private List<String> roles;
}

