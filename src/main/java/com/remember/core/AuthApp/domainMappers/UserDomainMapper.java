package com.remember.core.AuthApp.domainMappers;

import com.remember.core.AuthApp.domains.User;
import com.remember.core.AuthApp.repositories.RolesRepository;
import com.remember.core.AuthApp.requestDtos.UserRequestDto;

import com.remember.core.utils.uriToIdConverter.UriToIdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDomainMapper {
    private final RolesRepository rolesRepository;
    private final UriToIdConverter uriToIdConverter;

    public User toEntity(UserRequestDto ro) {
        User user = User.builder()
                .username(ro.getUsername())
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
