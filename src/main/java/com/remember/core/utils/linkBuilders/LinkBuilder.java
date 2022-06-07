package com.remember.core.utils.linkBuilders;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class LinkBuilder{

    public static Link getListLink(String resource) {

        StringBuilder str = new StringBuilder("/");
        str.append(resource);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    public static Link getListLink(String baseUrl, String resource) {
        StringBuilder str = new StringBuilder(baseUrl);
        str.append("/"); str.append(resource);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    public static Link getDetailLink(String resource, Long id) {
        StringBuilder str = new StringBuilder("/");
        str.append(resource);
        str.append("/"); str.append(id);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    public static Link getDetailLink(String baseUrl, String resource, Long id) {
        StringBuilder str = new StringBuilder(baseUrl);

        str.append("/"); str.append(resource);
        str.append("/"); str.append(id);

        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    public static Link getServiceDetailLink(String baseUrl, String service, String resource, Long id) {
        StringBuilder str = new StringBuilder(baseUrl);

        str.append("/"); str.append(service);
        str.append("/"); str.append(resource);
        str.append("/"); str.append(id);

        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }
}
