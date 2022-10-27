package com.remember.core.controllers;

import com.remember.core.dtos.requests.AlgorithmRequest;
import com.remember.core.dtos.responses.AlgorithmResponse;
import com.remember.core.dtos.responses.DataResponse;
import com.remember.core.services.AlgorithmsService;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/algorithms")
public class AlgorithmsController extends AbstractController {
    private final AlgorithmsService algorithmsService;

    @GetMapping
    public DataResponse<?> findAll() {
        List<AlgorithmResponse> algorithms = algorithmsService.findAll();

        return DataResponse.of(CollectionModel.of(algorithms).add(Link.of(currentRequest()).withSelfRel()));
    }

    @PostMapping
    public DataResponse<?> create(@RequestBody AlgorithmRequest request) {
        return DataResponse.of(algorithmsService.create(request));
    }
}
