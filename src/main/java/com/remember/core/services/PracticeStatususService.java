package com.remember.core.services;

import com.remember.core.controllers.hateoasProcessors.PracticeStatusAssembler;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.dtos.responses.PracticeStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PracticeStatususService {
    private final PracticeStatusAssembler assembler;

    public CollectionModel<PracticeStatusResponse> findAll() {
        return assembler.toCollectionModel(PracticeStatus.findAll());
    }
}
