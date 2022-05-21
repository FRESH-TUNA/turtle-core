package com.remember.core.domains;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Question extends BaseTimeDomain{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String link;

    @Column
    private Integer level;

    @ManyToMany
    @JoinTable(name = "question_algorithm",
            joinColumns = @JoinColumn(name = "algorithm_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"))
    private List<Algorithm> algorithms = new ArrayList<>();
}
