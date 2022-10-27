package com.remember.core.utils;

import java.util.Map;

public class UrlBuilder {
    private String root;
    private String path;
    private StringBuilder query;

    public static UrlBuilder builder() {
        return new UrlBuilder();
    }

    private UrlBuilder() {
        this.root = "/";
        this.path = "";
        this.query = new StringBuilder();
    }

    public UrlBuilder ofRoot(String root) {
        this.root = root;
        return this;
    }

    public UrlBuilder ofPath(String path) {
        this.path = path;
        return this;
    }

    public UrlBuilder ofPath(String pathTemplate, Map<String, String> pathArgs) {
        String res = pathTemplate;
        for (String key : pathArgs.keySet()) {
            res = res.replace(String.format("{%s}", key), pathArgs.get(key));
        }
        this.path = res;
        return this;
    }

    public UrlBuilder ofQuery(String key, String value) {
        if(this.query.isEmpty())
            query.append('?');
        else
            query.append('&');

        query.append(key);
        query.append('=');
        query.append(value);

        return this;
    }

    public String build() {
        return root + path + query.toString();
    }
}
