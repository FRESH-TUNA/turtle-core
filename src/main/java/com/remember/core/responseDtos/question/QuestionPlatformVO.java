package com.remember.core.responseDtos.question;

import com.remember.core.domains.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Getter
@NoArgsConstructor
public class QuestionPlatformVO extends RepresentationModel<QuestionPlatformVO> {
    private Long id;
    private String name;
    private String link;

    public QuestionPlatformVO(Platform p) {
        this.id = p.getId();
        this.name = p.getName();
        this.link = p.getLink();
    }
}
