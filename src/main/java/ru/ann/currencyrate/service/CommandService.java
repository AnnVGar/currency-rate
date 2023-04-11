package ru.ann.currencyrate.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.ann.currencyrate.domain.Command;
import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.repository.CurrencyDataRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter
public class CommandService {
    private Command command;


    public List<CurrencyData> executeRate() {
        List<CurrencyData> result = new ArrayList<>();
        for (CurrencyName currency : command.getCurrencyNames()) {
            result.addAll(executeRateForCurrency(currency));
            command.getAlgorithmRate().clear();
        }
        return result;
    }

    public List<CurrencyData> executeRateForCurrency(CurrencyName currency) {
        log.debug("execute rate " + currency);
        List<CurrencyData> result = new ArrayList<>();
        for (int i = 0; i < command.getPeriod().getDayQuantity(); i++) {
            result.add(calculateELement(i, currency));
        }
        return result;
    }

    private CurrencyData calculateELement(int i, CurrencyName currency) {
        LocalDate date = command.getStartDate().plusDays(i);
        String name = currency.getName();
        BigDecimal unitCurs = command.getAlgorithmRate().getNextValue(CurrencyDataRepository.getListByCurrencyName(currency), date);
        return new CurrencyData(1, date, unitCurs, name, unitCurs);
    }

}
