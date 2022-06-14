package com.remember.core.domains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum PracticeStatus {
    /*
     * datas
     */
    SOLVED("성공", "#00ff00"),
    TIMEOUT("연습중", "#ffdb4d"),
    FAILED("실패", "#ff3333");

    private static List<PracticeStatus> allList;

    private final String STATUS;
    private final String COLOR;

    /*
     * constructor
     */
    PracticeStatus(String STATUS, String COLOR) {
        this.STATUS = STATUS;
        this.COLOR = COLOR;
    }

    public String getStatus() {
        return STATUS;
    }

    public String getColor() {
        return COLOR;
    }

    public static List<PracticeStatus> findAll() {
        if (Objects.isNull(allList)) {
            allList = new ArrayList<>(
                    Arrays.asList(SOLVED, TIMEOUT, FAILED)
            );
        }
        return allList;
    }
}
