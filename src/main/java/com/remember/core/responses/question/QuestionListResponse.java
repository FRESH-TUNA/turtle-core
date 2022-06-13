package com.remember.core.responses.question;

import com.remember.core.domains.Question;
import com.remember.core.responses.PracticeStatusResponse;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class QuestionListResponse extends RepresentationModel<QuestionListResponse> {
    private Long id;
    private String title;
    private String link;
    private String platform;
    private PracticeStatusResponse practiceStatus;

    public static QuestionListResponse of(Question question,
                                          PracticeStatusResponse practiceStatus) {
        return new QuestionListResponse(
                question.getId(), question.getTitle(), question.getLink(), question.getPlatform().getName(),
                practiceStatus
        );
    }
    private QuestionListResponse(Long id,
                                 String title,
                                 String link,
                                 String platform,
                                 PracticeStatusResponse practiceStatus) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.practiceStatus = practiceStatus;
        this.platform = platform;
    }
}
