package com.remember.core.utils;

import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Service;

@Service
public class ServerContext {
    public String getRoot() {
        return BasicLinkBuilder.linkToCurrentMapping().toString();
    }
}
