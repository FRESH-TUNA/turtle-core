package com.remember.core.services;

import com.remember.core.assemblers.PracticeStatususAssembler;
import com.remember.core.repositories.PracticeStatususRepository;
import com.remember.core.responseDtos.AlgorithmVO;
import com.remember.core.responseDtos.PracticeStatusVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PracticeStatususService {
    private final PracticeStatususRepository repository;
    private final PracticeStatususAssembler assembler;

    public CollectionModel<PracticeStatusVO> findAll() {
        return assembler.toCollectionModel(repository.findAll());
    }
}
