package ru.ann.domain;

import java.util.Arrays;

public enum Output {
    GRAPH,
    LIST;

    public static String valueForRegExp(){
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append(value).append("|"));
        return result.substring(0,result.length()-1);
    }
}
