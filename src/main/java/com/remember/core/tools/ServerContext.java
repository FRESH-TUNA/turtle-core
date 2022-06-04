package com.remember.core.tools;

import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ServerContext {
    public String getRoot() {
        return BasicLinkBuilder.linkToCurrentMapping().toString();
    }
}
