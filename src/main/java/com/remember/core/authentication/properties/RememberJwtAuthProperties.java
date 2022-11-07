package com.remember.core.authentication.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "remember-jwt-auth")
public class RememberJwtAuthProperties {
    private String tokenPrefix;

    private String secret;
    private String refreshSecret;
    private String refreshTokenCookieKey;

    private Long validityInSeconds;
    private Long refreshValidityInSeconds;

    public Long validityInMileseconds() {
        return validityInSeconds * 1000;
    }

    public Long refreshValidityInSeconds() {
        return refreshValidityInSeconds * 1000;
    }
}
