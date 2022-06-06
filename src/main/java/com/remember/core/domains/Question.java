package com.remember.core.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseTimeDomain{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String link;

    @ManyToOne
    @JoinColumn(name = "platform_id", nullable = false)
    private Platform platform;

    @ManyToOne
    @JoinColumn(name = "practice_status_id", nullable = false)
    private PracticeStatus practiceStatus;

    @ManyToMany
    @JoinTable(name = "question_algorithm",
            joinColumns = @JoinColumn(name = "algorithm_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"))
    private List<Algorithm> algorithms = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<PracticeLog> practiceLogs = new ArrayList<>();



    @Column(nullable = false)
    private Long user;

    /*
     * methods
     */
    public void addAlgorithm(Algorithm a) {
        this.algorithms.add(a);
    }

    public void partial_update(Question updated) {
        if(updated.practiceStatus != null)
            this.practiceStatus = updated.practiceStatus;
    }

    /*
     * contructors
     */
    @Builder
    public Question(
            Long id,
            String title,
            String link,
            Platform platform,
            PracticeStatus practiceStatus,
            Long user) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.platform = platform;
        this.practiceStatus = practiceStatus;
        this.user = user;
    }
}
