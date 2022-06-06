package com.remember.core.AuthApp.services;

import com.remember.core.security.RememberUserDetails;
import com.remember.core.AuthApp.repositories.UsersRepository;
import com.remember.core.AuthApp.domains.User;
import com.remember.core.AuthApp.requestDtos.UserRequestDto;
import com.remember.core.AuthApp.domainMappers.UserDomainMapper;
import com.remember.core.AuthApp.tools.SessionTool;
import com.remember.core.AuthApp.validations.AuthValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsersRepository userRepository;
    private final UserDomainMapper serializer;
    private final AuthValidations validations;
    private final SessionTool sessionTool;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다"));

        return RememberUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername()).password(user.getPassword())
                .roles(user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void registerNewUserAccount(UserRequestDto userRequestDto){
        validations.signUppasswordEqual(userRequestDto.getPassword(), userRequestDto.getMatchingPassword());

        User user = serializer.toEntity(userRequestDto);
        user = userRepository.save(user);

        UserDetails userDetails = RememberUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername()).password(user.getPassword())
                .roles(user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()))
                .build();

        sessionTool.setSession(userDetails);
    }
}
