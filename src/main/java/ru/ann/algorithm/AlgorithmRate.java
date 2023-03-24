package ru.ann.algorithm;

import ru.ann.domain.CurrencyData;

import java.math.BigDecimal;
import java.util.List;


/**
 * интерфейс расчета курса валюты
 *
 */
public interface AlgorithmRate {
    BigDecimal getNextValue(List<CurrencyData> list);
}
