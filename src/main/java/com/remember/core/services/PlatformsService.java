package com.remember.core.services;

import com.remember.core.domains.Platform;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.vos.PlatformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatformsService {
    @Autowired
    private PlatformsRepository repository;

    public List<PlatformVO> findAll() {
        return repository.findAll().stream().map(p -> new PlatformVO(p)).collect(Collectors.toList());
    }
}
