package ru.ann.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrencyData {

    private static final DateTimeFormatter uploadFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter toStringFormatter = DateTimeFormatter.ofPattern("eee dd.MM.yyyy");

    private int nominal;
    private LocalDate date;
    private double curs;
    private String cdx;
    private double value;

    public CurrencyData() {

    }

    public CurrencyData(String nominal, String date, String curs, String cdx) {

        this.nominal = Integer.parseInt(nominal);
        this.date = LocalDate.parse(date, uploadFormatter);
        this.curs = Double.parseDouble(curs.replace(",", "."));
        this.cdx = cdx;
        this.value = this.curs / this.nominal;
    }

    public int getNominal() {
        return nominal;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getCurs() {
        return curs;
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

    public void setCurs(double curs) {
        this.curs = curs;
    }

    public void setCdx(String cdx) {
        this.cdx = cdx;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return date.format(toStringFormatter) + " - " + Math.round(curs * 100.0) / 100.0;
    }


}
