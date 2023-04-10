package ru.ann.currencyrate.domain;

import lombok.Getter;
import ru.ann.currencyrate.api.AlgorithmRate;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.domain.type.Output;
import ru.ann.currencyrate.domain.type.Period;
import ru.ann.currencyrate.repository.CurrencyDataRepository;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Command {
    private final CurrencyName currencyName;
    private final Period period;
    private final LocalDate startDate;
    private final AlgorithmRate algorithmRate;
    private final List<CurrencyData> currencyDataList;
    private final Output output;

    public Command(CurrencyName currencyName, Period period, LocalDate date, AlgorithmRate algorithmRate) {
        this.currencyName = currencyName;
        this.period = period;
        this.startDate = date;
        this.algorithmRate = algorithmRate;
        this.currencyDataList = CurrencyDataRepository.getListByCurrencyName(this.currencyName);
        this.output = Output.LIST;
    }

    public Command(CurrencyName currencyName, Period period, LocalDate date, AlgorithmRate algorithmRate, String output) {
        this.currencyName = currencyName;
        this.period = period;
        this.startDate = date;
        this.algorithmRate = algorithmRate;
        this.currencyDataList = CurrencyDataRepository.getListByCurrencyName(this.currencyName);
        this.output = Output.valueOf(output);
    }

    @Override
    public String toString() {
        return currencyName.name() + " " + period.name() + " " + startDate.toString();
    }

}
