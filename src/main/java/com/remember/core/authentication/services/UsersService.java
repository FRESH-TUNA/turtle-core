package com.remember.core.authentication.services;

import com.remember.core.authentication.domains.ProviderType;
import com.remember.core.authentication.domains.Role;
import com.remember.core.authentication.domains.User;
import com.remember.core.authentication.dtos.RememberUserDetails;
import com.remember.core.authentication.dtos.UserIdentity;
import com.remember.core.authentication.dtos.UserRequest;
import com.remember.core.authentication.repositories.UsersRepository;
import com.remember.core.authentication.tools.SessionTool;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.exceptions.RememberAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionTool sessionTool;

    @Transactional
    public void registerNewUserAccount(UserRequest userRequest){
        User user = userRequest.toEntity(Role.ROLE_GUEST, ProviderType.LOCAL);

        if(userRepository.findByEmail(userRequest.getUsername()).isPresent())
            throw new RememberAuthenticationException(ErrorCode.SAME_EMAIL_EXSITED);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user = userRepository.save(user);

        UserDetails userDetails = RememberUserDetails.builder()
                .userIdentity(UserIdentity.of(user))
                .username(user.getEmail()).password(user.getPassword())
                .roles(Collections.singletonList(user.getRole().name()))
                .build();

        sessionTool.setSession(userDetails);
    }
}
