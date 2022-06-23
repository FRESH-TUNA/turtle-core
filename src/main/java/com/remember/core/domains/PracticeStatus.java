package com.remember.core.domains;

import java.util.List;
import java.util.Objects;

/*
 * https://stackoverflow.com/questions/32799282/does-the-enumvalues-allocate-memory-on-each-call
 */
public enum PracticeStatus {
    /*
     * datas
     */
    PERFECT("PERFECT", "#00ff00"),
    GREAT("GREAT", "#aaff00"),
    GOOD("GOOD", "#ffdb4d"),
    FAIL("FAIL", "#ff3333");


    private static List<PracticeStatus> allList = List.of(PracticeStatus.values());

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

    public static synchronized List<PracticeStatus> findAll() {
        return allList;
    }
}
