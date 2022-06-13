package com.remember.core.services;

import com.remember.core.assemblers.PracticeStatususAssembler;
import com.remember.core.domains.PracticeStatus;
import com.remember.core.responses.PracticeStatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PracticeStatususService {
    private final PracticeStatususAssembler assembler;

    public CollectionModel<PracticeStatusResponseDto> findAll() {
        return assembler.toCollectionModel(PracticeStatus.findAll());
    }
}
