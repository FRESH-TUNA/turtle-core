package com.remember.core.domains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum PracticeStatus {
    SOLVED,
    TIMEOUT,
    FAILED;

    private static List<PracticeStatus> allList;

    public static List<PracticeStatus> findAll() {
        if (Objects.isNull(allList)) {
            allList = new ArrayList<>(
                    Arrays.asList(SOLVED, TIMEOUT, FAILED)
            );
        }
        return allList;
    }
}
