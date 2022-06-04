package com.remember.core.services;

import com.remember.core.assemblers.PlatformsAssembler;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.responseDtos.PlatformResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformsService {
    private final PlatformsRepository repository;
    private final PlatformsAssembler assembler;

    public CollectionModel<PlatformResponseDto> findAll() {
        return assembler.toCollectionModel(repository.findAll());
    }
}
