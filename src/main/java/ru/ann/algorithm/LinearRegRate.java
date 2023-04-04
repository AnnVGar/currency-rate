package ru.ann.algorithm;

import lombok.NoArgsConstructor;
import ru.ann.domain.CurrencyData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
public class LinearRegRate implements AlgorithmRate{
//    LinerRegression linerRegression = new LinearRegression()

    @Override
    public BigDecimal getNextValue(List<CurrencyData> list, LocalDate date) {
        return null;
    }
}
