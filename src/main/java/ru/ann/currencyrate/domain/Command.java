package ru.ann.currencyrate.domain;

import lombok.Getter;
import ru.ann.currencyrate.api.AlgorithmRate;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.domain.type.Output;
import ru.ann.currencyrate.domain.type.Period;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Command {
    private final List<CurrencyName> currencyNames;
    private final Period period;
    private final LocalDate startDate;
    private final AlgorithmRate algorithmRate;
    private final Output output;

    public Command(List<CurrencyName> currencyNames, Period period, LocalDate date, AlgorithmRate algorithmRate) {
        this.currencyNames = currencyNames;
        this.period = period;
        this.startDate = date;
        this.algorithmRate = algorithmRate;
        this.output = Output.LIST;
    }

    public Command(List<CurrencyName> currencyNames, Period period, LocalDate date, AlgorithmRate algorithmRate, String output) {
        this.currencyNames = currencyNames;
        this.period = period;
        this.startDate = date;
        this.algorithmRate = algorithmRate;
        this.output = Output.valueOf(output);
    }

    @Override
    public String toString() {
        return currencyToString() + " " + period.name() + " " + startDate.toString();
    }

    private String currencyToString() {
        return String.join(",", currencyNames.stream().map(currencyName -> currencyName.getName()).toList());
    }

}
