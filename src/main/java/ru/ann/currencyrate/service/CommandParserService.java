package ru.ann.currencyrate.service;

import ru.ann.currencyrate.api.AlgorithmRate;
import ru.ann.currencyrate.common.CommandParserConstant;
import ru.ann.currencyrate.common.CurrencyConstant;
import ru.ann.currencyrate.domain.Command;
import ru.ann.currencyrate.domain.type.AlgorithmName;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.domain.type.Period;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandParserService {

    public List<Command> parserCommandFromLine (String commandLine){
        List<Command> result  =new ArrayList<>();
        String[] commandArr = commandLine.toUpperCase().split(CommandParserConstant.COMMAND_SPLIT);
        String[] currencyNameArr = commandArr[CommandParserConstant.CURRENCY_INDEX].split(CommandParserConstant.CURRENCY_SPLIT);
        Period period = parsePeriod(commandArr[CommandParserConstant.PERIOD_INDEX]);
        LocalDate startDate = parseStartDate(period, commandArr[CommandParserConstant.PERIOD_INDEX]);
        AlgorithmRate algorithmRate = parseAlgorithm(commandArr[CommandParserConstant.ALGORITHM_INDEX]);

        for (String name : currencyNameArr) {
            Command command;
            CurrencyName currencyName = CurrencyName.valueOf(name.toUpperCase());
            if (commandArr.length == CommandParserConstant.NO_OUTPUT_LENGTH) {
                command = new Command(currencyName, period, startDate, algorithmRate);
            } else {
                String output = commandArr[CommandParserConstant.OUTPUT_INDEX];
                command = new Command(currencyName, period, startDate, algorithmRate, output);
            }
            result.add(command);
        }
        return result;
    }

    private LocalDate parseStartDate(Period period, String date) {
        return period.equals(Period.DAY) ? LocalDate.parse(date, CurrencyConstant.UPLOAD_FORMATTER) : LocalDate.now().plusDays(1);
    }

    private AlgorithmRate parseAlgorithm(String algorithmName) {
        return AlgorithmName.initAlgorithm(algorithmName);
    }

    private Period parsePeriod(String period) {
        try {
            return Period.valueOf(period);
        } catch (IllegalArgumentException e) {
            return Period.valueOf("DAY");
        }
    }
}
