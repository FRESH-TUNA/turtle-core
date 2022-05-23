package com.remember.core.tools;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class LinkBuilderImpl implements LinkBuilder{
    @Override
    public Link getListLink(String resource) {
        StringBuilder str = new StringBuilder("/");
        str.append(resource);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    @Override
    public Link getListLink(String baseUrl, String resource) {
        StringBuilder str = new StringBuilder(baseUrl);
        str.append("/"); str.append(resource);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    @Override
    public Link getDetailLink(String resource, Long id) {
        StringBuilder str = new StringBuilder("/");
        str.append(resource);
        str.append("/"); str.append(id);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    @Override
    public Link getDetailLink(String baseUrl, String resource, Long id) {
        StringBuilder str = new StringBuilder(baseUrl);

        str.append("/"); str.append(resource);
        str.append("/"); str.append(id);

        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }
}
