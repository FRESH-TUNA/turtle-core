package com.remember.core.authentication.authToken;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTokenResponseDto {
    private String accessToken;
    private String refreshToken;

    public static AuthTokenResponseDto ofLogin(String accessToken, String refreshToken) {
        return new AuthTokenResponseDto(accessToken, refreshToken);
    }

    public static AuthTokenResponseDto ofAccess(String accessToken) {
        return new AuthTokenResponseDto(accessToken, null);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
