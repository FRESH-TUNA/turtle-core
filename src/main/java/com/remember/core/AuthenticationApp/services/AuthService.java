package com.remember.core.AuthenticationApp.services;

import com.remember.core.AuthenticationApp.domains.ProviderType;
import com.remember.core.AuthenticationApp.domains.Role;
import com.remember.core.exceptions.AuthenticationException;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.AuthenticationApp.dtos.RememberUserDetails;
import com.remember.core.AuthenticationApp.repositories.UsersRepository;
import com.remember.core.AuthenticationApp.domains.User;
import com.remember.core.AuthenticationApp.dtos.UserRequestDto;

import com.remember.core.AuthenticationApp.tools.SessionTool;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsersRepository userRepository;
    private final SessionTool sessionTool;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.NAME_OF_USER_NOTFOUND));

        return RememberUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername()).password(user.getPassword())
                .roles(Collections.singletonList(user.getRole().name()))
                .build();
    }

    @Transactional
    public void registerNewUserAccount(UserRequestDto userRequestDto){
        User user = userRequestDto.toEntity(Role.ROLE_GUEST, ProviderType.LOCAL);

        if(userRepository.findByEmail(userRequestDto.getUsername()).isPresent())
            throw new AuthenticationException(ErrorCode.SAME_EMAIL_EXSITED);

        user = userRepository.save(user);

        UserDetails userDetails = RememberUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername()).password(user.getPassword())
                .roles(Collections.singletonList(user.getRole().name()))
                .build();

        sessionTool.setSession(userDetails);
    }
}
