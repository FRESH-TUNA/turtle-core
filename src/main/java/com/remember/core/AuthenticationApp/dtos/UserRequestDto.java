package com.remember.core.AuthenticationApp.dtos;

import com.remember.core.AuthenticationApp.domains.ProviderType;
import com.remember.core.AuthenticationApp.domains.Role;
import com.remember.core.AuthenticationApp.domains.User;
import com.remember.core.validatiors.PasswordSafe;
import com.remember.core.validatiors.PasswordsEqualConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 참고자료
 * https://sanghye.tistory.com/36
 */


@AllArgsConstructor // for ModelAttribute
//@NoArgsConstructor // for requestbody processing jackson
@Getter
@PasswordsEqualConstraint(message = "패스워드가 일치하지 않습니다.")
public class UserRequestDto {
    @PasswordSafe(message = "패스워드가 약합니다")
    @NotBlank(message="패스워드를 입력해주세요")
    private String password;

    @NotBlank(message="원하시는 페스워드를 다시 입력해주세요")
    private String matchingPassword;


    @NotBlank(message = "원하시는 이메일을 입력해주세요")
    @Email(regexp = ".+[@].+[\\.].+", message="mail의 형식을 지켜주세요")
    private String username;

    public User toEntity(Role role, ProviderType providerType) {
        User user = User.builder()
                .email(getUsername())
                .role(role)
                .providerType(providerType)
                .build();
        return user;
    }
}

