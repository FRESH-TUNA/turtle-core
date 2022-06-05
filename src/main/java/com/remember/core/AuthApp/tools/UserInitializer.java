package com.remember.core.AuthApp.tools;

import com.remember.core.AuthApp.domains.User;
import com.remember.core.AuthApp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Component
public class UserInitializer implements ApplicationRunner {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
                .username("")
                .password(passwordEncoder.encode(""))
                .build();

        userRepository.save(user);
    }
}
