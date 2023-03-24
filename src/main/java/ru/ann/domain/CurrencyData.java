package ru.ann.domain;


import ru.ann.util.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


public class CurrencyData implements Comparable<CurrencyData>{

    private static final RoundingMode roundigMode = RoundingMode.HALF_UP;

    private int nominal;
    private LocalDate date;
    private BigDecimal curs;
    private String cdx;
    private BigDecimal value;


    public CurrencyData() {

    }

    public CurrencyData(int nominal, LocalDate date, BigDecimal curs, String cdx, BigDecimal value) {
        this.nominal = nominal;
        this.date = date;
        this.curs = curs;
        this.cdx = cdx;
        this.value = value;
    }

    public int getNominal() {
        return nominal;
    }

    public LocalDate getDate() {
        return date;
    }


    public String getCdx() {
        return cdx;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public void setCdx(String cdx) {
        this.cdx = cdx;
    }

    public BigDecimal getCurs() {
        return curs;
    }

    public void setCurs(BigDecimal curs) {
        this.curs = curs;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return date.format(DateUtils.TO_STRING_FORMATTER) + " - " + value.setScale(2, roundigMode);
    }

    @Override
    public int compareTo(CurrencyData o2) {
        return this.getDate().isAfter(o2.getDate()) ? -1 : 1;
    }


}
