package com.remember.core.tools;

import org.springframework.hateoas.Link;

public interface NestedLinkBuilder {
    public Link getListLink(String ParentResource, Long parentId, String resource);
    public Link getListLink(String baseUrl, String ParentResource, Long parentId, String resource);
    public Link getDetailLink(String ParentResource, Long parentId, String resource, Long id);
    public Link getDetailLink(String baseUrl, String ParentResource, Long parentId, String resource, Long id);
}
