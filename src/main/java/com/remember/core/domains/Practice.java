package com.remember.core.domains;

import com.remember.core.security.domains.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Practice extends BaseTimeDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @OneToMany(mappedBy = "practice")
    private List<PracticeStatus> statuss = new ArrayList<>();

    // security
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
