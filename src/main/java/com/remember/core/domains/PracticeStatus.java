package com.remember.core.domains;

import javax.persistence.*;

public class PracticeStatus extends BaseTimeDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String status;

    @Column(length = 100, nullable = false)
    private String color;

    @ManyToOne
    @JoinColumn(name = "practice_id", nullable = false)
    private Practice practice;
}
