package com.remember.core.services;

import com.remember.core.repositories.AlgorithmsRepository;
import com.remember.core.vos.AlgorithmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlgorithmsService {
    @Autowired
    private AlgorithmsRepository repository;

    public List<AlgorithmVO> findAll() {
        return repository.findAll().stream().map(p -> new AlgorithmVO(p)).collect(Collectors.toList());
    }
}
