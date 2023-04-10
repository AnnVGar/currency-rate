package ru.ann.currencyrate.service.algorithm;

import lombok.NoArgsConstructor;
import ru.ann.currencyrate.api.AlgorithmRate;
import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.Period;
import ru.ann.currencyrate.lib.LinearRegression;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * класс расчета курса валюты
 * линейную регрессию
 * за последний месяц
 */
@NoArgsConstructor
public class LinearRegRate implements AlgorithmRate {
    private LinearRegression linearRegression;
    private double[] valueArr;
    private final double[] dateArr = new double[31];

    @Override
    public BigDecimal getNextValue(List<CurrencyData> list, LocalDate date) {
        if (linearRegression == null) {
            fillDateArr();
            valueArr = getMonthArrayFromCurrencyData(list);
            linearRegression = new LinearRegression(dateArr, valueArr);
        }
        int index = Period.MONTH.getDayQuantity() - date.getDayOfMonth();
        return BigDecimal.valueOf(linearRegression.predict(valueArr[index]));
    }

    private void fillDateArr() {
        for (int i = 0; i < 31; i++) {
            dateArr[i] = 31 - i;
        }
    }

    private double[] getMonthArrayFromCurrencyData(List<CurrencyData> list) {
        return list.stream()
                .sorted()
                .limit(Period.MONTH.getDayQuantity())
                .mapToDouble(data -> data.getUnitCurs().doubleValue())
                .toArray();

    }

    @Override
    public void clear() {
        linearRegression = null;
    }
}
