package com.remember.core.AuthApp.services;

import com.remember.core.AuthApp.domains.ProviderType;
import com.remember.core.AuthApp.domains.User;
import com.remember.core.AuthApp.OAuth2UserInfos.OAuth2UserInfo;
import com.remember.core.AuthApp.exceptions.OAuthProviderMissMatchException;
import com.remember.core.AuthApp.factories.OAuth2UserInfoFactory;
import com.remember.core.AuthApp.repositories.UsersRepository;
import com.remember.core.security.RememberUserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        Optional<User> userWrapper = userRepository.findByEmail(userInfo.getEmail());

        if (userWrapper.isPresent()) {
            User savedUser = userWrapper.get();

            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + savedUser.getProviderType() + " account to login."
                );
            }
            savedUser.oauthUserUpdate(userInfo.getId(), userInfo.getImageUrl());
        } else {
            userRepository.save(userInfo.toEntity());
        }

        return (OAuth2User) RememberUserDetails.builder()
                .username(userInfo.getEmail())
                .attributes(userInfo.getAttributes());
    }
}
