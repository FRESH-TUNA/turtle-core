package com.remember.core.utils;

import org.springframework.hateoas.server.mvc.BasicLinkBuilder;

public class ServerContext {
    public static String getRoot() {
        return BasicLinkBuilder.linkToCurrentMapping().toString();
    }
}
