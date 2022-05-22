package com.remember.core.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PracticeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String status;

    @Column(length = 100, nullable = false)
    private String color;

    /*
     * contructors
     */
    @Builder
    public PracticeStatus(
            Long id,
            String status,
            String color) {
        this.id = id;
        this.status = status;
        this.color = color;
    }
}
