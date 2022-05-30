package com.remember.core.vos;

import com.remember.core.domains.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class AlgorithmVO extends RepresentationModel<AlgorithmVO> {
    private Long id;
    private String name;

    public AlgorithmVO(Algorithm algorithm) {
        this.id = algorithm.getId();
        this.name = algorithm.getName();
    }
}
