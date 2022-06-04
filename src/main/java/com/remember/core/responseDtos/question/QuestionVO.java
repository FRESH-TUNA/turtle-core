package com.remember.core.responseDtos.question;

import com.remember.core.domains.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class QuestionVO extends RepresentationModel<QuestionVO> {
    private Long id;
    private String title;
    private String link;
    private QuestionPlatformVO platform;
    private Integer level;
    private QuestionPracticeStatusVO practiceStatus;
    private List<QuestionAlgorithmsVO> algorithms;

    public QuestionVO(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.link = q.getLink();
        this.level = q.getLevel();

        this.platform = new QuestionPlatformVO(q.getPlatform());
        this.practiceStatus = new QuestionPracticeStatusVO(q.getPracticeStatus());
        this.algorithms = q.getAlgorithms()
                .stream()
                .map(a -> new QuestionAlgorithmsVO(a))
                .collect(Collectors.toList());
    }
}