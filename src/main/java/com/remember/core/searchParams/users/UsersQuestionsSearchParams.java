package com.remember.core.searchParams.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsersQuestionsSearchParams {
    private String title;
    private Long practiceStatus;

    //user side
    private String nickname;
}
