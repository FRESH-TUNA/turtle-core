package com.remember.core.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor // for ModelAttribute
//@NoArgsConstructor // for requestbody processing jackson
@Getter
public class QuestionsRO {
    private String title;
    private String link;
    private Integer level;

    private String platform;
    private String practiceStatus;
    private List<String> algorithms;
}
