package com.remember.core.services;

import com.remember.core.assemblers.AlgorithmsAssembler;
import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.responses.AlgorithmResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlgorithmsService {
    private final AlgorithmsRepository repository;
    private final AlgorithmsAssembler assembler;

    public CollectionModel<AlgorithmResponseDto> findAll() {
        return assembler.toCollectionModel(repository.findAll());
    }
}
