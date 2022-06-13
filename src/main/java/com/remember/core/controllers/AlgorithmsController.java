package com.remember.core.controllers;

import com.remember.core.requests.AlgorithmRequest;
import com.remember.core.responses.AlgorithmResponse;
import com.remember.core.services.AlgorithmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/algorithms")
public class AlgorithmsController {
    private final AlgorithmsService algorithmsService;

    @GetMapping
    public CollectionModel<AlgorithmResponse> findAll() {
        return algorithmsService.findAll();
    }

    @PostMapping
    public AlgorithmResponse create(@RequestBody AlgorithmRequest request) {
        return algorithmsService.create(request);
    }
}
