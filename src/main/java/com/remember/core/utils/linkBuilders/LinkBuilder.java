package com.remember.core.utils.linkBuilders;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;

public class LinkBuilder{
    public static Link getListLink(String baseUrl, String resource) {
        StringBuilder str = new StringBuilder(baseUrl);

        str.append("/");
        str.append(resource);

        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }
    public static Link getDetailLink(String baseUrl, String resource, Object id) {
        StringBuilder str = new StringBuilder(baseUrl);

        str.append("/");
        str.append(resource);
        str.append("/");
        str.append(id);

        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }
}
