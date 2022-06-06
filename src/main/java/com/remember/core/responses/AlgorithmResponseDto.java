package com.remember.core.responses;

import com.remember.core.domains.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class AlgorithmResponseDto extends RepresentationModel<AlgorithmResponseDto> {
    private Long id;
    private String name;

    public AlgorithmResponseDto(Algorithm algorithm) {
        this.id = algorithm.getId();
        this.name = algorithm.getName();
    }
}
