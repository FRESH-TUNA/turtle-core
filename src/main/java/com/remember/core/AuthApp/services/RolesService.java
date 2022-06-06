package com.remember.core.AuthApp.services;

import com.remember.core.AuthApp.domains.Role;
import com.remember.core.AuthApp.repositories.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolesService {
    private final RolesRepository rolesRepository;

    public Role getAdminRole() {
        return rolesRepository.findByName("ROLE_ADMIN").get();
    }

    public Role getGuestRole() {
        return rolesRepository.findByName("ROLE_GUEST").get();
    }
}
