package com.remember.core.controllers;

import com.remember.core.responses.AlgorithmResponseDto;
import com.remember.core.services.AlgorithmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/algorithms")
public class AlgorithmsController {
    private final AlgorithmsService algorithmsService;

    @GetMapping
    public CollectionModel<AlgorithmResponseDto> findAll() {
        return algorithmsService.findAll();
    }
}
