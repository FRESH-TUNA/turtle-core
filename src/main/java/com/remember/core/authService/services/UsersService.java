package com.remember.core.authService.services;

import com.remember.core.authService.repositories.UsersRepository;
import com.remember.core.authService.domains.User;
import com.remember.core.authService.ros.UserRO;
import com.remember.core.authService.serializers.UsersSerializer;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository userRepository;
    private final UsersSerializer serializer;

    @Transactional
    public void create(UserRO userRO){
        User user = serializer.toEntity(userRO);
        user = userRepository.save(user);
    }
}
