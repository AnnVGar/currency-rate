package ru.ann.domain;


import lombok.*;
import ru.ann.util.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyData implements Comparable<CurrencyData>{

    private static final RoundingMode roundigMode = RoundingMode.HALF_UP;

    private int nominal;
    private LocalDate date;
    private BigDecimal curs;
    private String cdx;
    private BigDecimal value;

    @Override
    public String toString() {
        return date.format(DateUtils.TO_STRING_FORMATTER) + " - " + value.setScale(2, roundigMode);
    }

    @Override
    public int compareTo(CurrencyData o2) {
        return this.getDate().isAfter(o2.getDate()) ? -1 : 1;
    }


}
