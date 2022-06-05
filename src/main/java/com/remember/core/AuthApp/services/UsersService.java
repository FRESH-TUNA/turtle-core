package com.remember.core.AuthApp.services;

import com.remember.core.AuthApp.repositories.UsersRepository;
import com.remember.core.AuthApp.domains.User;
import com.remember.core.AuthApp.requestDtos.UserRequestDto;
import com.remember.core.AuthApp.domainMappers.UserDomainMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository userRepository;
    private final UserDomainMapper serializer;

    @Transactional
    public void create(UserRequestDto userRequestDto){
        User user = serializer.toEntity(userRequestDto);
        user = userRepository.save(user);
    }
}
