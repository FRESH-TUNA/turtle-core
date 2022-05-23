package com.remember.core.vos.user;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class UserQuestionsVO extends RepresentationModel<UserQuestionsVO> {
    private Long id;
    private String title;
    private String link;
    private String platform;
    private Integer level;
    private PracticeStatus practiceStatus;

    public UserQuestionsVO(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.link = q.getLink();
        this.level = q.getLevel();
        this.platform = q.getPlatform().getName();
        this.practiceStatus = q.getPracticeStatus();
    }
}
