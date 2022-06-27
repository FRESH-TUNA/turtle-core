package com.remember.core.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/*
 * bugs
 * 제약조건들을 먼저(우선하여) 배치해야 문제가 안발생함
 */
@AllArgsConstructor // for ModelAttribute
//@NoArgsConstructor // for requestbody processing jackson
@Getter
public class QuestionRequest {
    @NotBlank(message="문제의 이름을 입력해주세요")
    private String title;

    @NotNull(message="플랫폼을 선택해주세요")
    private Long platform;

    @NotBlank(message="문제를 풀어본 결과를 선택해주세요")
    private String practiceStatus;

    private String link;

    private List<Long> algorithms;
}
