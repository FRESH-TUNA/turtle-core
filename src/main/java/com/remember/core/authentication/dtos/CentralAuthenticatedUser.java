package com.remember.core.authentication.dtos;

import com.remember.core.authentication.authToken.JWTAuthTokenProvider;
import com.remember.core.authentication.domains.User;
import com.remember.core.domains.UserIdentityField;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class CentralAuthenticatedUser implements UserDetails, OAuth2User, RememberUser {
    private Long id;
    private String password;
    private List<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    /*
     * Spring UserDetails
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * OAuth2User
     */
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.getUsername();
    }


    /**
     * RememberUser
     */
    @Override
    public UserIdentityField toUserIdentityField() {
        return UserIdentityField.builder().
                user(id)
                .build();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isSameUser(RememberUser user) {
        return this.id.equals(user.getId());
    }

    @Override
    public String getConcatenatedAuthoritiesByComma() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    /**
     * Factories
     */
    public static CentralAuthenticatedUser of(User user) {
        return new CentralAuthenticatedUser(
                user.getId(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())),
                null
        );
    }

    public static CentralAuthenticatedUser of(OAuth2User oAuth2User, User user) {
        return new CentralAuthenticatedUser(
                user.getId(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())),
                oAuth2User.getAttributes()
        );
    }

    public static CentralAuthenticatedUser of(Claims claims) {
        if(!claims.containsKey(JWTAuthTokenProvider.AUTHORITIES_KEY))
            return new CentralAuthenticatedUser(
                    Long.valueOf(claims.getSubject()),
                    null,
                    Collections.EMPTY_LIST,
                    null
            );

        String authoritiesString = claims.get(JWTAuthTokenProvider.AUTHORITIES_KEY).toString();

        List<GrantedAuthority> authorities = Arrays.stream(authoritiesString
                        .split(","))
                .map(a -> new SimpleGrantedAuthority(a))
                .collect(Collectors.toList());

        return new CentralAuthenticatedUser(
                Long.valueOf(claims.getSubject()),
                null,
                authorities,
                null
        );
    }

    //세션 중복로그인을 막기 위해 확인이 필요한대 hashcode, equals 메소드 재정의가 필요하다.
    @Override
    public boolean equals(Object otherUser) {
        CentralAuthenticatedUser other = (CentralAuthenticatedUser) otherUser;
        return this.id.equals(other.id);
    }
}
