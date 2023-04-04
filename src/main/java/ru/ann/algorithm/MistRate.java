package ru.ann.algorithm;

import lombok.NoArgsConstructor;
import ru.ann.domain.CurrencyData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ru.ann.algorithm.MoonRate.getNearestDataByDate;
@NoArgsConstructor
public class MistRate implements AlgorithmRate {
    private static final int YEARS_DEPTH = 17;

    @Override
    public BigDecimal getNextValue(List<CurrencyData> list, LocalDate date) {
        int yearRandom =  ThreadLocalRandom.current().nextInt(1, YEARS_DEPTH + 1);
                return getNearestDataByDate(date.minusYears(yearRandom), list).getValue();
    }


}
