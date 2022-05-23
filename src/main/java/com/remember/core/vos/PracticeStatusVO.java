package com.remember.core.vos;

import com.remember.core.domains.PracticeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class PracticeStatusVO extends RepresentationModel<PracticeStatusVO> {
    private Long id;
    private String status;
    private String color;

    public PracticeStatusVO(PracticeStatus p) {
        this.id = p.getId();
        this.status = p.getStatus();
        this.color = p.getColor();
    }
}
