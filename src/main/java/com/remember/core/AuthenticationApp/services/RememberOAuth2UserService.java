package com.remember.core.AuthenticationApp.services;

import com.remember.core.AuthenticationApp.domains.ProviderType;
import com.remember.core.AuthenticationApp.domains.User;
import com.remember.core.AuthenticationApp.dtos.OAuth2UserInfos.OAuth2UserInfo;
import com.remember.core.exceptions.RememberAuthenticationException;
import com.remember.core.AuthenticationApp.factories.OAuth2UserInfoFactory;
import com.remember.core.AuthenticationApp.repositories.UsersRepository;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.AuthenticationApp.dtos.RememberUserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RememberOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Optional<User> userWrapper = userRepository.findByEmail(userInfo.getEmail());
        User savedUser;

        /*
         * 이미 가입했으면 업데이트, 신규유저인 경우 회원가입 진행
         */
        if (userWrapper.isPresent()) {
            savedUser = userWrapper.get();
            if (providerType != savedUser.getProviderType())
                throw new RememberAuthenticationException(ErrorCode.OAUTH_PROVIDER_MISMATCH);
            savedUser.oauthUserUpdate(userInfo.getId(), userInfo.getImageUrl());

        } else
            savedUser = userRepository.save(userInfo.toEntity());

        return RememberUserDetails.builder()
                .id(savedUser.getId())
                .username(userInfo.getEmail())
                .attributes(userInfo.getAttributes())
                .roles(Collections.singletonList(savedUser.getRole().name()))
                .build();
    }
}
