package com.remember.core.services;

import com.remember.core.assemblers.PracticeStatusAssembler;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.responses.PracticeStatusResponse;
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
