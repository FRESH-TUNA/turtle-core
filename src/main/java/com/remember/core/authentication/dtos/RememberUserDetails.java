package com.remember.core.authentication.dtos;

import com.remember.core.domains.UserIdentityField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
public class RememberUserDetails implements UserDetails, OAuth2User, RememberUserDetailsSupport {
    private UserIdentity userIdentity;
    private String username;
    private String password;
    private List<String> roles;
    private Map<String, Object> attributes;

    /*
     * UserDetails
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    //세션 중복로그인을 막기 위해 확인이 필요한대 hashcode, equals 메소드 재정의가 필요하다.
    @Override
    public boolean equals(Object otherUser) {
        RememberUserDetails other = (RememberUserDetails) otherUser;
        return other.userIdentity.equals(userIdentity);
    }

    /*
     * OAuth2User
     */
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.username;
    }


    /*
     * RememberUserDetailsSupport
     */
    @Override
    public UserIdentityField toUserIdentityField() {
        return UserIdentityField.builder().
                user(userIdentity.getId())
                .build();
    }

    @Override
    public boolean isSameUser(UserIdentityField user) {
        UserIdentity userIdentity = UserIdentity.of(user);
        return this.userIdentity.equals(userIdentity);
    }
}
