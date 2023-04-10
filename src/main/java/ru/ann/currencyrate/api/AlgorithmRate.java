package ru.ann.currencyrate.api;

import ru.ann.currencyrate.domain.CurrencyData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/**
 * интерфейс расчета курса валюты
 */
public interface AlgorithmRate {

    default void clear() {
    }

    BigDecimal getNextValue(List<CurrencyData> list, LocalDate date);
}
