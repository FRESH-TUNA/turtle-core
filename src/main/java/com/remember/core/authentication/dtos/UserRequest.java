package com.remember.core.authentication.dtos;

import com.remember.core.authentication.domains.ProviderType;
import com.remember.core.authentication.domains.Role;
import com.remember.core.authentication.domains.User;
import com.remember.core.validators.PasswordSafe;
import com.remember.core.validators.PasswordsEqualConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 참고자료
 * https://sanghye.tistory.com/36
 */


@AllArgsConstructor // for ModelAttribute
@NoArgsConstructor // for requestbody processing jackson
@Getter
@PasswordsEqualConstraint(message = "패스워드가 일치하지 않습니다.")
public class UserRequest {
    @PasswordSafe(message = "패스워드가 약합니다")
    @NotBlank(message="패스워드를 입력해주세요")
    private String password;

    @NotBlank(message="원하시는 페스워드를 다시 입력해주세요")
    private String matchingPassword;


    @NotBlank(message = "원하시는 이메일을 입력해주세요")
    @Email(regexp = ".+[@].+[\\.].+", message="mail의 형식을 지켜주세요")
    private String email;

    public User toLocalGuestUser() {
        User user = User.builder()
                .email(this.getEmail())
                .role(Role.ROLE_GUEST)
                .username(this.getEmail())
                .providerType(ProviderType.LOCAL)
                .build();
        return user;
    }

    public LoginRequest toLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        return loginRequest;
    }
}
