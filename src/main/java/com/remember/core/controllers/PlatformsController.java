package com.remember.core.controllers;

import com.remember.core.requests.PlatformRequest;
import com.remember.core.responses.PlatformResponseDto;
import com.remember.core.services.PlatformsService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platforms")
public class PlatformsController {
    private final PlatformsService platformsService;

    @GetMapping
    public CollectionModel<PlatformResponseDto> findAll() {
        return platformsService.findAll();
    }

    @PostMapping
    public PlatformResponseDto create(@RequestBody PlatformRequest request) {
        return platformsService.create(request);
    }
}
