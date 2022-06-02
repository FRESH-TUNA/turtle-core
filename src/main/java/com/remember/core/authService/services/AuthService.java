package com.remember.core.authService.services;

import com.remember.core.authService.RememberUserDetails;
import com.remember.core.authService.repositories.UsersRepository;
import com.remember.core.authService.domains.User;
import com.remember.core.authService.ros.UserRO;
import com.remember.core.authService.serializers.UsersSerializer;
import com.remember.core.authService.tools.users.AfterRegisterProcessor;
import com.remember.core.authService.validations.AuthValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsersRepository userRepository;
    private final UsersSerializer serializer;
    private final AuthValidations validations;
    private final AfterRegisterProcessor afterRegisterProcessor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다"));

        return new RememberUserDetails(user);
    }

    @Transactional
    public void registerNewUserAccount(UserRO userRO){
        validations.signUppasswordEqual(userRO.getPassword(), userRO.getMatchingPassword());

        User user = serializer.toEntity(userRO);
        user = userRepository.save(user);
        UserDetails userDetails = new RememberUserDetails(user);
        afterRegisterProcessor.setSession(userDetails);
    }
}
