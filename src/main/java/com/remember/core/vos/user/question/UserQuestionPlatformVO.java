package com.remember.core.vos.user.question;

import com.remember.core.domains.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


@Getter
@NoArgsConstructor
public class UserQuestionPlatformVO extends RepresentationModel<UserQuestionPlatformVO> {
    private Long id;
    private String name;
    private String link;

    public UserQuestionPlatformVO(Platform p) {
        this.id = p.getId();
        this.name = p.getName();
        this.link = p.getLink();
    }
}
