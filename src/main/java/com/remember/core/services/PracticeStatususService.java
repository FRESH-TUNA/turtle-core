package com.remember.core.services;

import com.remember.core.repositories.PracticeStatususRepository;
import com.remember.core.vos.PracticeStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PracticeStatususService {
    @Autowired
    private PracticeStatususRepository repository;

    public List<PracticeStatusVO> findAll() {
        return repository.findAll().stream().map(p -> new PracticeStatusVO(p)).collect(Collectors.toList());
    }
}
