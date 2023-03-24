package ru.ann.domain;

import java.util.Arrays;

public enum Period {

    TOMORROW(1),
    WEEK(7);

    private final int dayQuantity;

    Period(int dayQuantity) {
        this.dayQuantity = dayQuantity;
    }

    public int getDayQuantity() {
        return dayQuantity;
    }

    public static String valueForRegExp(){
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append("\\b").append(value).append("\\b|"));
        return result.substring(0,result.length()-1);
    }
}
