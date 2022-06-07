package com.remember.core.AuthApp.OAuth2UserInfos;

import com.remember.core.AuthApp.domains.Role;
import com.remember.core.AuthApp.domains.User;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo{
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public User toEntity() {
        return User.builder()
                .username(getId())
                .email(getEmail())
                .picture(getImageUrl())
                .role(Role.ROLE_GUEST)
                .build();
    }
}
