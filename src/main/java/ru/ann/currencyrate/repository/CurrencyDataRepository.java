package ru.ann.currencyrate.repository;

import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.CurrencyName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyDataRepository {

    public static final Map<CurrencyName, List<CurrencyData>> currencyMap = new HashMap<>();

    public static Map<CurrencyName, List<CurrencyData>> getCurrencyMap() {
        return currencyMap;
    }

    public static List<CurrencyData> getListByCurrencyName(CurrencyName currencyName) {
        return getCurrencyMap().get(currencyName);
    }


}
