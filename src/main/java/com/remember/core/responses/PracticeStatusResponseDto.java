package com.remember.core.responses;

import com.remember.core.domains.PracticeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class PracticeStatusResponseDto extends RepresentationModel<PracticeStatusResponseDto> {
    private String id;
    private String status;
    private String color;

    public PracticeStatusResponseDto(PracticeStatus p) {
        this.id = p.name();
        this.status = p.getStatus();
        this.color = p.getColor();
    }
}
