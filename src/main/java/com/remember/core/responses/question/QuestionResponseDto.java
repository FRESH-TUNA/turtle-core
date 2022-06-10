package com.remember.core.responses.question;

import com.remember.core.domains.Question;
import com.remember.core.responses.AlgorithmResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class QuestionResponseDto extends RepresentationModel<QuestionResponseDto> {
    private Long id;
    private String title;
    private String link;
    private String practiceStatus;
    private QuestionPlatformResponseDto platform;
    private List<AlgorithmResponseDto> algorithms = new ArrayList<>();

    public QuestionResponseDto(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.link = q.getLink();
        this.platform = new QuestionPlatformResponseDto(q.getPlatform());
        this.practiceStatus = q.getPracticeStatus().name();
    }

    public void addAlgorithm(AlgorithmResponseDto algorithm) {
        this.algorithms.add(algorithm);
    }
}