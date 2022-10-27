package com.remember.core.services;

import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.dtos.requests.PlatformRequest;
import com.remember.core.dtos.responses.PlatformResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatformsService {
    private final PlatformsRepository repository;

    public List<PlatformResponse> findAll() {
        return repository.findAll().stream().map(PlatformResponse::of).collect(Collectors.toList());
    }

    public PlatformResponse create(PlatformRequest request) {
        return PlatformResponse.of(repository.save(request.toEntity()));
    }
}
