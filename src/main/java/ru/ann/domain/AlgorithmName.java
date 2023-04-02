package ru.ann.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ann.algorithm.*;

import java.util.Arrays;
import java.util.Locale;

@RequiredArgsConstructor
@Getter
public enum AlgorithmName {
    AVERAGE(new AverageRate()),
    MOON(new MoonAlgorithm()),
    MIST(new MistAlgorithm()),
    REGRESS(new LinearRegAlgorithm());

    private final AlgorithmRate algorithmRate;

    public static String valueForRegExp(){
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append(value).append("|"));
        return result.substring(0,result.length()-1);
    }

    public static AlgorithmName get (String name) {
        return AlgorithmName.get(name.toUpperCase(Locale.ROOT));
    }


}
