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

    public Command parserCommandFromLine(String commandLine) {
        String[] commandArr = commandLine.toUpperCase().split(CommandParserConstant.COMMAND_SPLIT);
        String[] currencyNameArr = commandArr[CommandParserConstant.CURRENCY_INDEX].split(CommandParserConstant.CURRENCY_SPLIT);
        List<CurrencyName> currencyNames = listFromNameArr(currencyNameArr);
        Period period = parsePeriod(commandArr[CommandParserConstant.PERIOD_INDEX]);
        LocalDate startDate = parseStartDate(period, commandArr[CommandParserConstant.PERIOD_INDEX]);
        Command command;
        AlgorithmRate algorithmRate = parseAlgorithm(commandArr[CommandParserConstant.ALGORITHM_INDEX]);
        if (commandArr.length == CommandParserConstant.NO_OUTPUT_LENGTH) {
            command = new Command(currencyNames, period, startDate, algorithmRate);
        } else {
            String output = commandArr[CommandParserConstant.OUTPUT_INDEX];
            command = new Command(currencyNames, period, startDate, algorithmRate, output);
        }
        return command;
    }

    private List<CurrencyName> listFromNameArr(String[] currencyNameArr) {
        List<CurrencyName> result = new ArrayList<>();
        for (String name : currencyNameArr) {
            result.add(CurrencyName.valueOf(name));
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
