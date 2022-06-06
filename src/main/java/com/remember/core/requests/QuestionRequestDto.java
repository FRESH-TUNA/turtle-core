package com.remember.core.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor // for ModelAttribute
//@NoArgsConstructor // for requestbody processing jackson
@Getter
public class QuestionRequestDto {
    @NotBlank(message="문제의 이름을 입력해주세요")
    private String title;

    private String link;

    @NotNull(message="플랫폼을 선택해주세요")
    private String platform;

    @NotNull(message="문제를 풀어본 결과를 선택해주세요")
    private String practiceStatus;

    private List<String> algorithms;
}
