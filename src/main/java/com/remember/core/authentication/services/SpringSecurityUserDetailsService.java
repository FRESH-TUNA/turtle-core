package com.remember.core.authentication.services;

import com.remember.core.authentication.domains.ProviderType;
import com.remember.core.authentication.domains.User;
import com.remember.core.authentication.dtos.CentralAuthenticatedUser;
import com.remember.core.authentication.exceptions.RememberAuthenticationException;
import com.remember.core.authentication.repositories.UserRepository;
import com.remember.core.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndProviderType(username, ProviderType.LOCAL)
                .orElseThrow(() -> new RememberAuthenticationException(ErrorCode.BAD_EMAIL_PASSWORD));

        return CentralAuthenticatedUser.of(user);
    }
}
