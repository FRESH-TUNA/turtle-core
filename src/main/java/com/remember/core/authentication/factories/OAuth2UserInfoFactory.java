package com.remember.core.authentication.factories;
import com.remember.core.authentication.dtos.OAuth2UserInfos.GoogleOAuth2UserInfo;
import com.remember.core.authentication.domains.ProviderType;
import com.remember.core.authentication.dtos.OAuth2UserInfos.OAuth2UserInfo;
import java.util.Map;


public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
