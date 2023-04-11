package ru.ann.currencyrate.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.ann.currencyrate.common.CurrencyConstant;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class CurrencyData implements Comparable<CurrencyData> {

    private final int nominal;
    private final LocalDate date;
    private final BigDecimal course;
    private final String name;
    private final BigDecimal unitCurs;

    @Override
    public String toString() {
        return date.format(CurrencyConstant.TO_STRING_FORMATTER) + " - " + unitCurs.setScale(CurrencyConstant.SCALE, CurrencyConstant.ROUNDING_MODE);
    }

    @Override
    public int compareTo(CurrencyData o2) {
        return this.getDate().isAfter(o2.getDate()) ? -1 : 1;
    }


}
