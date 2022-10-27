package com.remember.core.services;

import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.dtos.requests.AlgorithmRequest;
import com.remember.core.dtos.responses.AlgorithmResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlgorithmsService {
    private final AlgorithmsRepository repository;

    public List<AlgorithmResponse> findAll() {
        return repository.findAll().stream().map(AlgorithmResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public AlgorithmResponse create(AlgorithmRequest request) {
        return AlgorithmResponse.of(repository.save(request.toEntity()));
    }
}
