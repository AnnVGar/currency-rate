package ru.ann.domain;

import java.util.Arrays;

public enum Period {
    DAY(1),
    TOMORROW(1),
    WEEK(7),
    MONTH(30);


    private final int dayQuantity;

    Period(int dayQuantity) {
        this.dayQuantity = dayQuantity;
    }

    public int getDayQuantity() {
        return dayQuantity;
    }

    public static String valueForRegExp() {
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append(value).append("|"));
        return result.substring(0, result.length() - 1);
    }
}
