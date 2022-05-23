package com.remember.core.ros.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserQuestionsRO {
    private String title;
    private String link;
    private Integer level;

    private String platform;
    private String practiceStatus;
}
