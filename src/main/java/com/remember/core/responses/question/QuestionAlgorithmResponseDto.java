package com.remember.core.responses.question;

import com.remember.core.domains.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class QuestionAlgorithmResponseDto extends RepresentationModel<QuestionAlgorithmResponseDto> {
    private Long id;
    private String name;

    public QuestionAlgorithmResponseDto(Algorithm a) {
        this.id = a.getId();
        this.name = a.getName();
    }
}
