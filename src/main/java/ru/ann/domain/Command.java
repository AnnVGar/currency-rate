package ru.ann.domain;

import lombok.Getter;
import ru.ann.algorithm.AlgorithmRate;
import ru.ann.respository.CurrencyDataRespository;

import java.time.LocalDate;
import java.util.*;

@Getter
public class Command {
    private final CurrencyName currencyName;
    private final Period period;
    private final LocalDate startDate;
    private final AlgorithmRate algorithmRate;
    private final List<CurrencyData> currencyDataList;
    private final Output output;

    public Command(String currencyName, String period, LocalDate date, AlgorithmRate algorithmRate) {
        this.currencyName = CurrencyName.valueOf(currencyName.toUpperCase(Locale.ROOT));
        this.period = Period.valueOf(period.toUpperCase(Locale.ROOT));
        this.startDate = date;
        this.algorithmRate = algorithmRate;
        this.currencyDataList = CurrencyDataRespository.getListByCurrencyName(this.currencyName);
        this.output = Output.valueOf("LIST");
    }

    public Command(String currencyName, String period, LocalDate date, AlgorithmRate algorithmRate,String output) {
        this.currencyName = CurrencyName.valueOf(currencyName.toUpperCase(Locale.ROOT));
        this.period = Period.valueOf(period.toUpperCase(Locale.ROOT));
        this.startDate = date;
        this.algorithmRate = algorithmRate;
        this.currencyDataList = CurrencyDataRespository.getListByCurrencyName(this.currencyName);
        this.output = Output.valueOf(output);
    }

}
