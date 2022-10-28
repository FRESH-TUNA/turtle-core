package com.remember.core.services;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.dtos.responses.PracticeStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PracticeStatususService {
    public List<PracticeStatusResponse> findAll() {
        return PracticeStatus.findAll().stream().map(PracticeStatusResponse::of).collect(Collectors.toList());
    }
}
