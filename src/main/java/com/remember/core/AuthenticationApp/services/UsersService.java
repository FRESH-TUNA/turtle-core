package com.remember.core.AuthenticationApp.services;

import com.remember.core.AuthenticationApp.domains.ProviderType;
import com.remember.core.AuthenticationApp.domains.Role;
import com.remember.core.AuthenticationApp.domains.User;
import com.remember.core.AuthenticationApp.dtos.RememberUserDetails;
import com.remember.core.AuthenticationApp.dtos.UserRequestDto;
import com.remember.core.AuthenticationApp.repositories.UsersRepository;
import com.remember.core.AuthenticationApp.tools.SessionTool;
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
    public void registerNewUserAccount(UserRequestDto userRequestDto){
        User user = userRequestDto.toEntity(Role.ROLE_GUEST, ProviderType.LOCAL);

        if(userRepository.findByEmail(userRequestDto.getUsername()).isPresent())
            throw new RememberAuthenticationException(ErrorCode.SAME_EMAIL_EXSITED);

        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        user = userRepository.save(user);

        UserDetails userDetails = RememberUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername()).password(user.getPassword())
                .roles(Collections.singletonList(user.getRole().name()))
                .build();

        sessionTool.setSession(userDetails);
    }
}
