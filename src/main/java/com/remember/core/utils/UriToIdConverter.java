package com.remember.core.utils;

public class UriToIdConverter {
    public static Long convert(String uri) {
        String[] data = uri.toString().split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
