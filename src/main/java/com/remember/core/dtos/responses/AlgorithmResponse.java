package com.remember.core.dtos.responses;

import com.remember.core.domains.Algorithm;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlgorithmResponse extends RepresentationModel<AlgorithmResponse> {
    private Long id;
    private String name;

    public static AlgorithmResponse of(Algorithm algorithm) {
        return new AlgorithmResponse(algorithm.getId(), algorithm.getName());
    }
}
