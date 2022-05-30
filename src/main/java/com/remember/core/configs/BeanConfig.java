package com.remember.core.configs;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.remember.core.tools.uriToIdConverter.BasicUriToIdConverter;
import com.remember.core.tools.uriToIdConverter.UriToIdConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class BeanConfig {
    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }

    @Bean
    public UriToIdConverter uriToIdConverter() {
        return new BasicUriToIdConverter();
    }
}
