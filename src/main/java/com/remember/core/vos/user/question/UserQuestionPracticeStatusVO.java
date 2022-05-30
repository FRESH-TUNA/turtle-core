package com.remember.core.vos.user.question;

import com.remember.core.domains.PracticeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;



@Getter
@NoArgsConstructor
public class UserQuestionPracticeStatusVO extends RepresentationModel<UserQuestionPracticeStatusVO> {
    private Long id;
    private String status;
    private String color;

    public UserQuestionPracticeStatusVO(PracticeStatus ps) {
        this.id = ps.getId();
        this.status = ps.getStatus();
        this.color = ps.getColor();
    }
}
