package ru.ann.algorithm;

import lombok.NoArgsConstructor;
import ru.ann.domain.CurrencyData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
public class MoonRate implements AlgorithmRate {

    @Override
    public BigDecimal getNextValue(List<CurrencyData> list, LocalDate date) {
        return getNearestDataByDate(date.minusYears(1), list).getValue();
    }

    public static CurrencyData getNearestDataByDate(LocalDate date, List<CurrencyData> list) {
        CurrencyData currencyData = null;
        int dayCounter = 0;
        while (currencyData == null) {
            int finalDayCounter = dayCounter;
            List<CurrencyData> newList = list.stream().filter(data -> data.getDate().isEqual(date.minusDays(finalDayCounter))).limit(1).toList();
            if (!newList.isEmpty()) {
                currencyData = newList.get(0);
            }
            dayCounter++;
        }
        return currencyData;
    }
}
