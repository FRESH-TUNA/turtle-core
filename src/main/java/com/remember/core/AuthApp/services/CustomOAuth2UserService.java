package com.remember.core.AuthApp.services;

import com.remember.core.AuthApp.dtos.OAuthAttributes;
import com.remember.core.AuthApp.domains.User;
import com.remember.core.AuthApp.dtos.SessionUser;
import com.remember.core.AuthApp.repositories.UsersRepository;
import com.remember.core.security.userDetails.OauthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UsersRepository usersRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser (직렬화된 dto 클래스 사용)

        return OauthUserDetails.builder()
                .roles(user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()))
                .name(user.getEmail()).attributes(attributes.getAttributes()).id(user.getId()).build();
    }

    // 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OAuthAttributes attributes){
        Optional<User> user = usersRepository.findByEmail(attributes.getEmail());

        if(!user.isPresent()) {
            return usersRepository.save(attributes.toEntity());
            //role 설정
        }

        User realUser = user.get();
        if(!realUser.isOauth()) {
            //exception
            return realUser;
        }
        else {
            realUser.oauthUserUpdate(attributes.getName(), attributes.getPicture());
        }

        return usersRepository.save(realUser);
    }
}
