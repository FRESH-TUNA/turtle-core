package com.remember.core.controllers;

import com.remember.core.consts.Urls;
import com.remember.core.dtos.requests.PlatformRequest;

import com.remember.core.dtos.responses.DataResponse;
import com.remember.core.dtos.responses.PlatformResponse;
import com.remember.core.services.PlatformsService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Urls.PLATFORMS.ROOT)
public class PlatformsController extends AbstractController {
    private final PlatformsService platformsService;

    @GetMapping
    public DataResponse<?> findAll() {
        List<PlatformResponse> platforms = platformsService.findAll();

        return DataResponse.of(CollectionModel.of(platforms).add(Link.of(currentRequest()).withSelfRel()));
    }

    @PostMapping
    public DataResponse<?> create(@RequestBody PlatformRequest request) {
        return DataResponse.of(platformsService.create(request));
    }
}
