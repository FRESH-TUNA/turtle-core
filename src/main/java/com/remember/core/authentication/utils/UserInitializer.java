package com.remember.core.authentication.utils;

import com.remember.core.authentication.domains.User;
import com.remember.core.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Component
public class UserInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
                .nickname("")
                .password(passwordEncoder.encode(""))
                .build();

        userRepository.save(user);
    }
}
