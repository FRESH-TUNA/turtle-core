package com.remember.core.vos.user.question;

import com.remember.core.domains.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class UserQuestionAlgorithmsVO extends RepresentationModel<UserQuestionAlgorithmsVO> {
    private Long id;
    private String name;

    public UserQuestionAlgorithmsVO(Algorithm a) {
        this.id = a.getId();
        this.name = a.getName();
    }
}
