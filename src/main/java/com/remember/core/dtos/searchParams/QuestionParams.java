package com.remember.core.dtos.searchParams;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionParams {
    private String title;
    private String practiceStatus;
    private List<Long> algorithms;
}
