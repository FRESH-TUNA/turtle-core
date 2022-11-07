package com.remember.core.authentication.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "remember-oauth")
@Getter
@Setter
public class RememberOAuthProperties {
    private Set<String> oAuth2AuthorizedRedirectUris;
}
