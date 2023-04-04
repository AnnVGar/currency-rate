package ru.ann.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ann.algorithm.*;

import java.util.Arrays;
import java.util.Locale;

@RequiredArgsConstructor
@Getter
public enum AlgorithmName {
    AVERAGE("AverageRate"),
    MOON("MoonRate"),
    MIST("MistRate"),
    REGRESS("LinearRegRate");

    private final String algorithmClassName;
    private static final String packageName = "ru.ann.algorithm.";

    public static String valueForRegExp(){
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append(value).append("|"));
        return result.substring(0,result.length()-1);
    }

    public static String getFullAlgorithmClassName (String name) {
        return packageName+AlgorithmName.valueOf(name.toUpperCase()).getAlgorithmClassName();
    }


}
