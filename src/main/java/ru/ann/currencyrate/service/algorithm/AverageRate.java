package ru.ann.currencyrate.service.algorithm;

import lombok.NoArgsConstructor;
import ru.ann.currencyrate.api.AlgorithmRate;
import ru.ann.currencyrate.domain.CurrencyData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * класс расчета курса валюты
 * как средее арифметическое
 * за неделю
 */
@NoArgsConstructor
public class AverageRate implements AlgorithmRate {
    private static final int ROWS_FOR_RATE = 7;
    private static final int SCALE = 4;
    private final LinkedList<BigDecimal> valuesForRate = new LinkedList<>();

    @Override
    public BigDecimal getNextValue(List<CurrencyData> list, LocalDate date) {
        if (valuesForRate.isEmpty()) {
            initValuesForRate(list);
        }
        BigDecimal sum = valuesForRate.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal result = sum.divide(BigDecimal.valueOf(ROWS_FOR_RATE), SCALE, RoundingMode.HALF_UP);
        valuesForRate.addFirst(result);
        valuesForRate.removeLast();
        return result;
    }

    private void initValuesForRate(List<CurrencyData> list) {
        List<CurrencyData> sortedList = list.stream().sorted().limit(ROWS_FOR_RATE).toList();
        for (CurrencyData currencyData : sortedList) {
            valuesForRate.add(currencyData.getUnitCurs());
        }
    }

    @Override
    public void clear() {
        valuesForRate.clear();
    }
}
