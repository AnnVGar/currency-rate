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

    public static AlgorithmRate initAlgorithm(String name) {
        AlgorithmName algorithmName = AlgorithmName.valueOf(name);
        switch (algorithmName) {
            case AVERAGE:
                return new AverageRate();
            case MOON:
                return new MoonRate();
            case MIST:
                return new MistRate();
            case REGRESS:
                return new LinearRegRate();
            default:
                return null;
        }
    }


}
