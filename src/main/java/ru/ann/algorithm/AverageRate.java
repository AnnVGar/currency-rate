package ru.ann.algorithm;

import ru.ann.domain.CurrencyData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * класс расчета курса валюты
 * как средее арифметическое
 */
public class AverageRate implements AlgorithmRate {
    private static final int ROWS_FOR_RATE = 7;
    private static final int SCALE = 2;

    @Override
    public BigDecimal getNextValue(List<CurrencyData> list) {
        BigDecimal sum = list.stream()
                .sorted()
                .limit(ROWS_FOR_RATE)
                .map(CurrencyData::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(ROWS_FOR_RATE), SCALE, RoundingMode.HALF_UP);
    }
}
