package ru.ann.currencyrate.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Output {
    GRAPH("GRAPH"),
    LIST("LIST");

    private final String name;

    public static String valueForRegExp(){
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append(value).append("|"));
        return result.substring(0,result.length()-1);
    }
}
