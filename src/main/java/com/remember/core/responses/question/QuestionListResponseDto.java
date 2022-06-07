package com.remember.core.responses.question;

import com.remember.core.domains.Question;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class QuestionListResponseDto extends RepresentationModel<QuestionListResponseDto> {
    private Long id;
    private String title;
    private String link;
    private String platform;
    private String practiceStatus;

    public QuestionListResponseDto(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.link = q.getLink();
        this.platform = q.getPlatform().getName();
        this.practiceStatus = q.getPracticeStatus().name();
    }
}
