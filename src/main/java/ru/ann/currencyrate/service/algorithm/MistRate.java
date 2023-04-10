package ru.ann.currencyrate.service.algorithm;

import lombok.NoArgsConstructor;
import ru.ann.currencyrate.api.AlgorithmRate;
import ru.ann.currencyrate.domain.CurrencyData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ru.ann.currencyrate.service.algorithm.MoonRate.getNearestDataByDate;

/**
 * класс расчета курса валюты
 * по дате рандомное количество лет назад
 */
@NoArgsConstructor
public class MistRate implements AlgorithmRate {
    private static final int YEARS_DEPTH = 17;

    @Override
    public BigDecimal getNextValue(List<CurrencyData> list, LocalDate date) {
        int yearRandom =  ThreadLocalRandom.current().nextInt(1, YEARS_DEPTH + 1);
                return getNearestDataByDate(date.minusYears(yearRandom), list).getUnitCurs();
    }


}
