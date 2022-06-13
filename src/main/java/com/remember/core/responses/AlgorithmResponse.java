package com.remember.core.responses;

import com.remember.core.domains.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@NoArgsConstructor
@Relation(collectionRelation = "algorithms")
public class AlgorithmResponse extends RepresentationModel<AlgorithmResponse> {
    private Long id;
    private String name;

    public AlgorithmResponse(Algorithm algorithm) {
        this.id = algorithm.getId();
        this.name = algorithm.getName();
    }
}
