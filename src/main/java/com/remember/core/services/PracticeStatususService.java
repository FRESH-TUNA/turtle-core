package com.remember.core.services;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.responses.PracticeStatusResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PracticeStatususService {
    public List<PracticeStatusResponseDto> findAll() {
        return PracticeStatus.findAll().stream()
                .map(PracticeStatusResponseDto::new)
                .collect(Collectors.toList());
    }
}
