package com.remember.core.responseDtos.question;

import com.remember.core.domains.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class QuestionAlgorithmsVO extends RepresentationModel<QuestionAlgorithmsVO> {
    private Long id;
    private String name;

    public QuestionAlgorithmsVO(Algorithm a) {
        this.id = a.getId();
        this.name = a.getName();
    }
}
