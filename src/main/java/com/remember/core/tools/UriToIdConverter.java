package com.remember.core.tools;

public class UriToIdConverter {
    protected Long convertURItoID(String uri) {
        String[] data = uri.split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
