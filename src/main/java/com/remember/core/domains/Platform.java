package com.remember.core.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Platform extends BaseTimeDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String link;

    /*
     * contructors
     */
    @Builder
    public Platform(Long id, String name, String link) {
        this.id = id;
        this.link = link;
        this.name = name;
    }
}
