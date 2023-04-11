package ru.ann.currencyrate.service;

import ru.ann.currencyrate.api.AlgorithmRate;
import ru.ann.currencyrate.common.CommandParserConstant;
import ru.ann.currencyrate.common.CurrencyConstant;
import ru.ann.currencyrate.domain.Command;
import ru.ann.currencyrate.domain.type.AlgorithmName;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.domain.type.Output;
import ru.ann.currencyrate.domain.type.Period;
import ru.ann.currencyrate.service.algorithm.AverageRate;
import ru.ann.currencyrate.service.algorithm.LinearRegRate;
import ru.ann.currencyrate.service.algorithm.MistRate;
import ru.ann.currencyrate.service.algorithm.MoonRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandParserService {

    public Command parserCommandFromLine(String commandLine) {
        String[] commandArr = commandLine.toUpperCase().split(CommandParserConstant.COMMAND_SPLIT);
        String[] currencyNameArr = commandArr[CommandParserConstant.CURRENCY_INDEX].split(CurrencyConstant.CURRENCY_SPLIT);
        List<CurrencyName> currencyNames = listFromNameArr(currencyNameArr);
        Period period = parsePeriod(commandArr[CommandParserConstant.PERIOD_INDEX]);
        LocalDate startDate = parseStartDate(period, commandArr[CommandParserConstant.PERIOD_INDEX]);
        Command command;
        AlgorithmRate algorithmRate = parseAlgorithm(commandArr[CommandParserConstant.ALGORITHM_INDEX]);
        if (commandArr.length == CommandParserConstant.NO_OUTPUT_LENGTH) {
            command = new Command(currencyNames, period, startDate, algorithmRate);
        } else {
            Output output = Output.valueOf(commandArr[CommandParserConstant.OUTPUT_INDEX]);
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
        return initAlgorithm(algorithmName);
    }

    private Period parsePeriod(String period) {
        try {
            return Period.valueOf(period);
        } catch (IllegalArgumentException e) {
            return Period.DAY;
        }
    }

    private static AlgorithmRate initAlgorithm(String name) {
        AlgorithmName algorithmName = AlgorithmName.valueOf(name);
        return switch (algorithmName) {
            case AVERAGE -> new AverageRate();
            case MOON -> new MoonRate();
            case MIST -> new MistRate();
            case REGRESS -> new LinearRegRate();
            default -> null;
        };
    }
}
