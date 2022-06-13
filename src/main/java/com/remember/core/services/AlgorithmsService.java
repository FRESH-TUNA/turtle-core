package com.remember.core.services;

import com.remember.core.assemblers.AlgorithmAssembler;
import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.requests.AlgorithmRequest;
import com.remember.core.responses.AlgorithmResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlgorithmsService {
    private final AlgorithmsRepository repository;
    private final AlgorithmAssembler assembler;

    public CollectionModel<AlgorithmResponse> findAll() {
        return assembler.toCollectionModel(repository.findAll());
    }

    @Transactional
    public AlgorithmResponse create(AlgorithmRequest request) {
        return assembler.toModel(repository.save(request.toEntity()));
    }
}
