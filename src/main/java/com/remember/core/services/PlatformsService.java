package com.remember.core.services;

import com.remember.core.assemblers.PlatformsAssembler;
import com.remember.core.repositories.PlatformsRepository;
import com.remember.core.responseDtos.AlgorithmVO;
import com.remember.core.responseDtos.PlatformVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatformsService {
    private final PlatformsRepository repository;
    private final PlatformsAssembler assembler;

    public CollectionModel<PlatformVO> findAll() {
        return assembler.toCollectionModel(repository.findAll());
    }
}
