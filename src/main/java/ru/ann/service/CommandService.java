package ru.ann.service;

import ru.ann.domain.Command;
import ru.ann.domain.CurrencyData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandService {
    private List<Command> commandList = new ArrayList<>();

    public List<CurrencyData> executeRate() {
        List<CurrencyData> result= new ArrayList<>();
        for(Command command: commandList){
            result.addAll(executeRateForCommand(command));
        }
        return result;
    }


    public List<CurrencyData> executeRateForCommand(Command command) {
        List<CurrencyData> result= new ArrayList<>();
        for (int i = 0; i < command.getPeriod().getDayQuantity(); i++) {
            result.add(calculateELement(i, command));
        }
        return result;
    }

    private  CurrencyData calculateELement(int i, Command command) {
        CurrencyData newData = new CurrencyData();
        LocalDate date = command.getStartDate().plusDays(i);
        newData.setDate(date);
        newData.setCdx(command.getCurrencyName().getCdx());
        newData.setNominal(1);
        BigDecimal value = command.getAlgorithmRate().getNextValue(command.getCurrencyDataList(), date);
        newData.setValue(value);
        newData.setCurs(value);
        return newData;
    }

    public void addCommand(Command command){
        commandList.add(command);
    }


}
