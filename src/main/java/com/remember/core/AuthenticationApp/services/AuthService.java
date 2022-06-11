package com.remember.core.AuthenticationApp.services;

import com.remember.core.exceptions.RememberAuthenticationException;
import com.remember.core.exceptions.ErrorCode;
import com.remember.core.AuthenticationApp.dtos.RememberUserDetails;
import com.remember.core.AuthenticationApp.repositories.UsersRepository;
import com.remember.core.AuthenticationApp.domains.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RememberAuthenticationException(ErrorCode.BAD_EMAIL_PASSWORD));

        return RememberUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername()).password(user.getPassword())
                .roles(Collections.singletonList(user.getRole().name()))
                .build();
    }
}
