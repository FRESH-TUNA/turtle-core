package com.remember.core.utils.linkBuilders;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;


public class NestedLinkBuilder{
    public static Link getListLink(String ParentResource, Long parentId, String resource) {
        StringBuilder str = new StringBuilder("/");

        str.append(ParentResource);
        str.append("/"); str.append(parentId);
        str.append("/"); str.append(resource);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    public static Link getListLink(String baseUrl, String ParentResource, Long parentId, String resource) {
        StringBuilder str = new StringBuilder(baseUrl);

        str.append("/"); str.append(ParentResource);
        str.append("/"); str.append(parentId);
        str.append("/"); str.append(resource);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    public static Link getDetailLink(String ParentResource, Long parentId, String resource, Long id) {
        StringBuilder str = new StringBuilder("/");

        str.append(ParentResource);
        str.append("/"); str.append(parentId);
        str.append("/"); str.append(resource);
        str.append("/"); str.append(id);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }

    public static Link getDetailLink(String baseUrl, String ParentResource, Long parentId, String resource, Long id) {
        StringBuilder str = new StringBuilder(baseUrl);

        str.append("/"); str.append(ParentResource);
        str.append("/"); str.append(parentId);
        str.append("/"); str.append(resource);
        str.append("/"); str.append(id);
        return Link.of(str.toString(), IanaLinkRelations.SELF);
    }
}
