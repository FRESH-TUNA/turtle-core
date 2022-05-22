package com.remember.core.responseDtos.user;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@RequiredArgsConstructor
public class UserQuestionsResponseDto extends RepresentationModel<UserQuestionsResponseDto> {
    private String title;
    private String link;
    private String platform;
    private Integer level;
    private PracticeStatus practiceStatus;

    public UserQuestionsResponseDto(Question q) {
        this.title = q.getTitle();
        this.link = q.getLink();
        this.level = q.getLevel();
        this.platform = q.getPlatform().getName();
        this.practiceStatus = q.getPracticeStatus();
    }
}
