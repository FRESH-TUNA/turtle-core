package com.remember.core.services;

import com.remember.core.assemblers.PlatformAssembler;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.requests.PlatformRequest;
import com.remember.core.responses.PlatformResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformsService {
    private final PlatformsRepository repository;
    private final PlatformAssembler assembler;

    public CollectionModel<PlatformResponse> findAll() {
        return assembler.toCollectionModel(repository.findAll());
    }

    public PlatformResponse create(PlatformRequest request) {
        return assembler.toModel(repository.save(request.toEntity()));
    }
}
