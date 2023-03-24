package ru.ann.domain;

import ru.ann.algorithm.AlgorithmRate;
import ru.ann.respository.CurrencyDataRespository;

import java.util.*;

public class Command {
    private final CurrencyName currencyName;
    private final Period period;
    private final AlgorithmRate algorithmRate;
    private final List<CurrencyData> currencyDataList;

    public Command(String currencyName, String period, AlgorithmRate algorithmRate) {
        this.currencyName = CurrencyName.valueOf(currencyName.toUpperCase(Locale.ROOT));
        this.period = Period.valueOf(period.toUpperCase(Locale.ROOT));
        this.algorithmRate = algorithmRate;
        this.currencyDataList = CurrencyDataRespository.getListByCurrencyName(this.currencyName);
    }

    public CurrencyName getCurrencyName() {
        return currencyName;
    }

    public Period getPeriod() {
        return period;
    }

    public AlgorithmRate getAlgorithmRate() {
        return algorithmRate;
    }

    public List<CurrencyData> getCurrencyDataList() {
        return currencyDataList;
    }
}
