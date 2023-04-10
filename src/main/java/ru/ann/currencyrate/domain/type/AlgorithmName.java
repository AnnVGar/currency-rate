package ru.ann.currencyrate.domain.type;

import ru.ann.currencyrate.api.AlgorithmRate;
import ru.ann.currencyrate.service.algorithm.AverageRate;
import ru.ann.currencyrate.service.algorithm.LinearRegRate;
import ru.ann.currencyrate.service.algorithm.MistRate;
import ru.ann.currencyrate.service.algorithm.MoonRate;

import java.util.Arrays;

public enum AlgorithmName {
    AVERAGE,
    MOON,
    MIST,
    REGRESS;

    public static String valueForRegExp() {
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append(value).append("|"));
        return result.substring(0, result.length() - 1);
    }

}
