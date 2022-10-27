package com.remember.core.controllers;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractController {
    protected String currentRoot() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    }

    protected String currentRequestUri() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
    }

    protected String currentRequest() {
        return ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
    }
}
