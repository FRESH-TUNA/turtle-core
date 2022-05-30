package com.remember.core.tools.uriToIdConverter;

public class BasicUriToIdConverter implements UriToIdConverter{
    @Override
    public Long convert(String uri) {
        String[] data = uri.toString().split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
