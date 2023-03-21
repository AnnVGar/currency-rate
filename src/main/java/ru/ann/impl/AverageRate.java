package ru.ann.impl;

import ru.ann.core.AlgorithmRate;

import java.util.List;

/**
 * алгоритм расчета курса валюты
 * как средее арифметическое предыдущих семи значений
 *
 * @return результат расчета
 */
public class AverageRate implements AlgorithmRate {
    @Override
    public Double getNextValue(List<CurrencyData> list) {
        return list.stream().limit(7).mapToInt(x -> (int) (x.getValue() * 100)).average().getAsDouble() / 100.0;

    }
}
