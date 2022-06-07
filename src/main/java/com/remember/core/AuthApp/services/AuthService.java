package com.remember.core.AuthApp.services;

import com.remember.core.AuthApp.domains.ProviderType;
import com.remember.core.AuthApp.domains.Role;
import com.remember.core.AuthApp.exceptions.AuthenticationException;
import com.remember.core.AuthApp.exceptions.ErrorCode;
import com.remember.core.security.RememberUserDetails;
import com.remember.core.AuthApp.repositories.UsersRepository;
import com.remember.core.AuthApp.domains.User;
import com.remember.core.AuthApp.dtos.UserRequestDto;

import com.remember.core.AuthApp.tools.SessionTool;
import com.remember.core.AuthApp.validatiors.AuthValidatior;
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
    private final AuthValidatior validations;
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
        validations.signUppasswordEqual(userRequestDto.getPassword(), userRequestDto.getMatchingPassword());

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
