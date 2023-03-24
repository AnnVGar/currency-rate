package ru.ann.domain;


import java.util.Arrays;

public enum CurrencyName {
    TRY,
    USD,
    EUR;

    public static String valueForRegExp(){
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append("\\b").append(value).append("\\b|"));
        return result.substring(0,result.length()-1);
    }
}
