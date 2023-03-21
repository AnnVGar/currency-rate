package ru.ann.core;

import ru.ann.impl.CurrencyData;

import java.util.List;

@FunctionalInterface
public interface AlgorithmRate {
    Double getNextValue(List<CurrencyData> list);
}
