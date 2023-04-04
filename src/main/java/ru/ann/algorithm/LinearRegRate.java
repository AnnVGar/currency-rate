package ru.ann.algorithm;

import lombok.NoArgsConstructor;
import ru.ann.domain.CurrencyData;
import ru.ann.domain.Period;
import ru.ann.lib.LinearRegression;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * класс расчета курса валюты
 * линейную регрессию
 * за последний месяц
 */
@NoArgsConstructor
public class LinearRegRate implements AlgorithmRate{
    private LinearRegression linearRegression;
    private double[] valueArr;
    private double[] dateArr = new double[31];
    @Override
    public BigDecimal getNextValue(List<CurrencyData> list, LocalDate date) {
        if(linearRegression == null){
            fillDateArr();
            valueArr = getMonthArrayFromCurrencyData(list);
            linearRegression = new LinearRegression (dateArr,valueArr);
        }
        int index = Period.MONTH.getDayQuantity() -date.getDayOfMonth();
        return new BigDecimal(linearRegression.predict(valueArr[index]));
    }

    private void fillDateArr() {
        for (int i = 0; i <31 ; i++) {
            dateArr[i] = 31-i;
        }
    }

    private double[] getMonthArrayFromCurrencyData(List<CurrencyData> list) {
        return list.stream()
                .sorted()
                .limit(Period.MONTH.getDayQuantity())
                .mapToDouble(data ->data.getValue().doubleValue())
                .toArray();

    }
}
