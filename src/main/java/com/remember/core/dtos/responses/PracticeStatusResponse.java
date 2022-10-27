package com.remember.core.dtos.responses;

import com.remember.core.domains.PracticeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
public class PracticeStatusResponse extends RepresentationModel<PracticeStatusResponse> {
    private String id;
    private String status;
    private String color;

    private PracticeStatusResponse(String id, String status, String color) {
        this.id = id;
        this.status = status;
        this.color = color;
    }

    public static PracticeStatusResponse of(PracticeStatus p) {
        return new PracticeStatusResponse(p.name(), p.getStatus(), p.getColor());
    }
}
