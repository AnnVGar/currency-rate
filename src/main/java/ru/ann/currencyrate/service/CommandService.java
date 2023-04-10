package ru.ann.currencyrate.service;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.ann.currencyrate.common.ChartConstant;
import ru.ann.currencyrate.domain.Command;
import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.util.LineChart;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class CommandService {
    private final List<Command> commandList = new ArrayList<>();

    public List<CurrencyData> executeRate() {
        List<CurrencyData> result= new ArrayList<>();
        log.debug("Command count  "+commandList.size());
        for(Command command: commandList){
            result.addAll(executeRateForCommand(command));
        }
        return result;
    }


    public List<CurrencyData> executeRateForCommand(Command command) {
        log.debug("execute command "+command.toString());
        List<CurrencyData> result= new LinkedList<>();
        for (int i = 0; i < command.getPeriod().getDayQuantity(); i++) {
            result.add(calculateELement(i, command));
        }
        return result;
    }


    private  CurrencyData calculateELement(int i, Command command) {
        LocalDate date = command.getStartDate().plusDays(i);
        String name = command.getCurrencyName().getName();
        BigDecimal unitCurs = command.getAlgorithmRate().getNextValue(command.getCurrencyDataList(), date);
        return new CurrencyData(1,date,unitCurs,name,unitCurs);
    }

    public void addCommand(Command command){
        commandList.add(command);
    }


}
