package ru.ann.service;

import ru.ann.domain.Command;
import ru.ann.domain.CurrencyData;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommandService {
    private Command command;

    public void executeRate() {
        for (int i = 0; i < command.getPeriod().getDayQuantity(); i++) {
            addRateELement();
        }
    }

    public Command getCommand() {
        return command;
    }

    /**
     * Добавляем  прогнозируемый элемент в currencyDataList
     * поле элемента value расчитываем фунуцией algorithmRate.getNextValue()
     */
    private void addRateELement() {
        CurrencyData previous = command.getCurrencyDataList().stream().sorted().limit(1).toList().get(0);
        CurrencyData newData = new CurrencyData();
        newData.setDate(calculateNextDate(previous.getDate()));
        newData.setCdx(previous.getCdx());
        newData.setNominal(previous.getNominal());
        BigDecimal value = command.getAlgorithmRate().getNextValue(command.getCurrencyDataList());
        newData.setValue(value);
        newData.setCurs(value.multiply(new BigDecimal(previous.getNominal())));
        command.getCurrencyDataList().add(newData);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    private LocalDate calculateNextDate(LocalDate previousDate){
        return previousDate.isAfter(LocalDate.now()) ? previousDate.plusDays(1) : LocalDate.now().plusDays(1);
    }
}
