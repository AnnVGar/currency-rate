package ru.ann.respository;

import ru.ann.domain.CurrencyData;
import ru.ann.domain.CurrencyName;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyDataRespository {

    public static Map<CurrencyName, List<CurrencyData>> currencyMap = new HashMap<>();

    public static Map<CurrencyName, List<CurrencyData>> getCurrencyMap() {
        return currencyMap;
    }

    public static List<CurrencyData> getListByCurrencyName(CurrencyName currencyName) {
        return getCurrencyMap().get(currencyName);
    }



}
