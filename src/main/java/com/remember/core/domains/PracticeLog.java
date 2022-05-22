package com.remember.core.domains;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class PracticeLog extends BaseTimeDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "practice_id", nullable = false)
    private Practice practice;

    @ManyToOne
    @JoinColumn(name = "practice_status_id", nullable = false)
    private PracticeStatus practiceStatus;
}
