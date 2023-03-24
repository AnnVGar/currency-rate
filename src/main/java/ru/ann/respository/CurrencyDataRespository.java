package ru.ann.respository;

import ru.ann.domain.CurrencyData;
import ru.ann.domain.CurrencyName;


import java.util.*;

public class CurrencyDataRespository {

   public static Map<CurrencyName, List<CurrencyData>> currencyMap = new HashMap<>();

   public static Map<CurrencyName, List<CurrencyData>> getCurrencyMap() {
      return currencyMap;
   }

   public static List<CurrencyData> getListByCurrencyName(CurrencyName currencyName){
      return getCurrencyMap().get(currencyName);
   }
}
