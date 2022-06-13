package com.remember.core.responses.question;

import com.remember.core.domains.Question;
import com.remember.core.responses.AlgorithmResponse;
import com.remember.core.responses.PlatformResponse;
import com.remember.core.responses.PracticeStatusResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionResponse extends RepresentationModel<QuestionResponse> {
    private Long id;
    private String title;
    private String link;
    private PracticeStatusResponse practiceStatus;
    private PlatformResponse platform;
    private List<AlgorithmResponse> algorithms;

    public static QuestionResponse of(Question question,
                                      PlatformResponse platform,
                                      PracticeStatusResponse practiceStatus,
                                      List<AlgorithmResponse> algorithms) {
        return new QuestionResponse(
                question.getId(), question.getTitle(), question.getLink(),
                practiceStatus, platform, algorithms
        );
    }

    private QuestionResponse(Long id,
                             String title,
                             String link,
                             PracticeStatusResponse practiceStatus,
                             PlatformResponse platform,
                             List<AlgorithmResponse> algorithms) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.practiceStatus = practiceStatus;
        this.platform = platform;
        this.algorithms = algorithms;
    }
}
