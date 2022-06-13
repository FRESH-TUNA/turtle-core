package com.remember.core.responses.question;

import com.remember.core.domains.Question;
import com.remember.core.responses.AlgorithmResponseDto;
import com.remember.core.responses.PlatformResponseDto;
import com.remember.core.responses.PracticeStatusResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionResponseDto extends RepresentationModel<QuestionResponseDto> {
    private Long id;
    private String title;
    private String link;
    private PracticeStatusResponseDto practiceStatus;
    private PlatformResponseDto platform;
    private List<AlgorithmResponseDto> algorithms;

    public static QuestionResponseDto of(Question question,
                                         PlatformResponseDto platform,
                                         PracticeStatusResponseDto practiceStatus,
                                         List<AlgorithmResponseDto> algorithms) {
        return new QuestionResponseDto(
                question.getId(), question.getTitle(), question.getLink(),
                practiceStatus, platform, algorithms
        );
    }

    public static QuestionResponseDto of(Question question,
                                         PlatformResponseDto platform,
                                         PracticeStatusResponseDto practiceStatus) {
        return new QuestionResponseDto(
                question.getId(), question.getTitle(), question.getLink(),
                practiceStatus, platform
        );
    }


    private QuestionResponseDto(Long id,
                                String title,
                                String link,
                                PracticeStatusResponseDto practiceStatus,
                                PlatformResponseDto platform,
                                List<AlgorithmResponseDto> algorithms) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.practiceStatus = practiceStatus;
        this.platform = platform;
        this.algorithms = algorithms;
    }

    private QuestionResponseDto(Long id,
                                String title,
                                String link,
                                PracticeStatusResponseDto practiceStatus,
                                PlatformResponseDto platform) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.practiceStatus = practiceStatus;
        this.platform = platform;
        this.algorithms = new ArrayList<>();
    }
}
