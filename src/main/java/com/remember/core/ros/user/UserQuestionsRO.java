package com.remember.core.ros.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor // for ModelAttribute
@NoArgsConstructor // for requestbody processing jackson
@Getter
public class UserQuestionsRO {
    private String title;
    private String link;
    private Integer level;

    private String platform;
    private String practiceStatus;
    private List<String> algorithms;
}
