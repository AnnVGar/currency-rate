package ru.ann.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CurrencyName {
    ADM("Армянский драм"),
    BGN("Болгарский лев"),
    EUR("Евро"),
    TRY("Турецкая лира"),
    USD("Доллар США");

   private final String cdx;

    public static String valueForRegExp(){
        StringBuilder result = new StringBuilder();
        Arrays.stream(values()).forEach(value -> result.append(value).append("|"));
        return result.substring(0,result.length()-1);
    }
}
