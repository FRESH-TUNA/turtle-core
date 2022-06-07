package com.remember.core.responses;

import com.remember.core.domains.PracticeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class PracticeStatusResponseDto extends RepresentationModel<PracticeStatusResponseDto> {
    private String status;

    public PracticeStatusResponseDto(PracticeStatus p) {
       this.status = p.name();
    }
}
