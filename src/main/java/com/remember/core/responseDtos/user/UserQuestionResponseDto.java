package com.remember.core.responseDtos.user;

import com.remember.core.domains.Platform;
import com.remember.core.domains.Question;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@RequiredArgsConstructor
public class UserQuestionResponseDto extends RepresentationModel<UserQuestionResponseDto> {

    private Long id;
    private String title;
    private String link;
    private Platform platform;
    private Integer level;

    public UserQuestionResponseDto(Question q) {

    }
}
