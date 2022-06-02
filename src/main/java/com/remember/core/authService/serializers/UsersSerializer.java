package com.remember.core.authService.serializers;

import com.remember.core.authService.domains.User;
import com.remember.core.authService.repositories.RolesRepository;
import com.remember.core.authService.ros.UserRO;

import com.remember.core.tools.uriToIdConverter.UriToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersSerializer {
    private final RolesRepository rolesRepository;
    private final UriToIdConverter uriToIdConverter;

    public User toEntity(UserRO ro) {
        User user = User.builder()
                .username(ro.getUsername())
                .nickname(ro.getNickname())
                .password(ro.getPassword())
                .build();
        return user;
    }

    public User addRoles(User user, List<String> roles) {
        if(roles == null) return user;
        for (String role : roles)
            user.addRole(rolesRepository.getById(uriToIdConverter.convert(role)));
        return user;
    }
}
