package com.remember.core.utils;

public class UriToIdConverter {
    public static Long convert(String uri) {
        System.out.println(uri);
        System.out.println("strange");
        String[] data = uri.toString().split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
