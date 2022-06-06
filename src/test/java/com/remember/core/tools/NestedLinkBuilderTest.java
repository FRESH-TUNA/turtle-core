package com.remember.core.tools;

import com.remember.core.utils.linkBuilders.NestedLinkBuilder;
import com.remember.core.utils.linkBuilders.NestedLinkBuilderImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import static org.assertj.core.api.Assertions.assertThat;

class NestedLinkBuilderTest {
    private final NestedLinkBuilder builder = new NestedLinkBuilderImpl();
    private final String ROOT = "";
    private final String parentResource = "parents";
    private final Long parentId = 1L;
    private final String resource = "resources";

    @Test
    @DisplayName("중첩 리소스 리스트 링크 빌드 테스트")
    void getListLink() {
        Link link = builder.getListLink(ROOT, parentResource, parentId, resource);
        assertThat(link.getHref()).isEqualTo("/parents/1/resources");
    }

    @Test
    @DisplayName("중첩 리소스 단일 링크 빌드 테스트")
    void getDetailLink() {
        Link link = builder.getDetailLink(ROOT, parentResource, parentId, resource, 1L);
        assertThat(link.getHref()).isEqualTo("/parents/1/resources/1");
    }
}
