package com.remember.core.utils;

import com.remember.core.consts.Urls;

import java.util.HashMap;
import java.util.Map;

public class UrlFacade {
    public static String USERS_ME_QUESTIONS_ID(String root, Long id) {
        Map<String, String> pathArgs = new HashMap<>();
        pathArgs.put("id", String.valueOf(id));

        return UrlBuilder.builder()
                .ofRoot(root)
                .ofPath(Urls.USERS.ME.QUESTIONS.ID, pathArgs)
                .build();
    }

    public static String PRACTICESTATUSUS_NAME(String root, String name) {
        Map<String, String> pathArgs = new HashMap<>();
        pathArgs.put("name", name);

        return UrlBuilder.builder()
                .ofRoot(root)
                .ofPath(Urls.PRACTICESTATUSUS.NAME, pathArgs)
                .build();
    }
}
