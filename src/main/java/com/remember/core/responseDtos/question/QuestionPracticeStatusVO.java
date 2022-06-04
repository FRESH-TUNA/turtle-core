package com.remember.core.responseDtos.question;

import com.remember.core.domains.PracticeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;



@Getter
@NoArgsConstructor
public class QuestionPracticeStatusVO extends RepresentationModel<QuestionPracticeStatusVO> {
    private Long id;
    private String status;
    private String color;

    public QuestionPracticeStatusVO(PracticeStatus ps) {
        this.id = ps.getId();
        this.status = ps.getStatus();
        this.color = ps.getColor();
    }
}
