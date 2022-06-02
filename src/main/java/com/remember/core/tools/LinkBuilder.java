package com.remember.core.tools;

import org.springframework.hateoas.Link;

public interface LinkBuilder {
    public Link getListLink(String resource);
    public Link getListLink(String baseUrl, String resource);
    public Link getDetailLink(String resource, Long id);
    public Link getDetailLink(String baseUrl, String resource, Long id);

    Link getServiceDetailLink(String baseUrl, String service, String resource, Long id);
}
