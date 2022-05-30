package com.remember.core.vos.user;

import com.remember.core.domains.PracticeStatus;
import com.remember.core.domains.Question;
import com.remember.core.vos.user.question.UserQuestionAlgorithmsVO;
import com.remember.core.vos.user.question.UserQuestionPlatformVO;
import com.remember.core.vos.user.question.UserQuestionPracticeStatusVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserQuestionVO extends RepresentationModel<UserQuestionVO> {
    private Long id;
    private String title;
    private String link;
    private UserQuestionPlatformVO platform;
    private Integer level;
    private UserQuestionPracticeStatusVO practiceStatus;
    private List<UserQuestionAlgorithmsVO> algorithms;

    public UserQuestionVO(Question q) {
        this.id = q.getId();
        this.title = q.getTitle();
        this.link = q.getLink();
        this.level = q.getLevel();

        this.platform = new UserQuestionPlatformVO(q.getPlatform());
        this.practiceStatus = new UserQuestionPracticeStatusVO(q.getPracticeStatus());
        this.algorithms = q.getAlgorithms()
                .stream()
                .map(a -> new UserQuestionAlgorithmsVO(a))
                .collect(Collectors.toList());
    }
}