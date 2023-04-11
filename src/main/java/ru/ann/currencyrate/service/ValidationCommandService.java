package ru.ann.currencyrate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.ann.currencyrate.common.CommandParserConstant;
import ru.ann.currencyrate.domain.type.AlgorithmName;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.domain.type.Output;
import ru.ann.currencyrate.domain.type.Period;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Getter
public class ValidationCommandService {

    private final Pattern CURRENCY_NAMES = Pattern.compile("(" + CurrencyName.valueForRegExp() + ")"
            + "(,(" + CurrencyName.valueForRegExp() + ")){0,4}");

    /**
     * проверка допустимости  команды из консоли
     */
    public String validateCommandFromLine(String message) {
        log.debug("Start to parse command: " + message);
        String[] commandLines = message.toUpperCase().split(CommandParserConstant.COMMAND_SPLIT);
        if (commandLines.length != 6 && commandLines.length != 8) {
            log.error("Wrong command " + message);
            return null;
        }
        List<Boolean> conditionsCheck = new ArrayList<>();
        conditionsCheck.add(operationName(commandLines[CommandParserConstant.OPERATION_INDEX]));
        conditionsCheck.add(currencyNames(commandLines[CommandParserConstant.CURRENCY_INDEX]));
        conditionsCheck.add(periodTitle(commandLines[CommandParserConstant.PERIOD_TITLE_INDEX]));
        conditionsCheck.add(periodName(commandLines[CommandParserConstant.PERIOD_INDEX]));
        conditionsCheck.add(algorithmTitle(commandLines[CommandParserConstant.ALGORITHM_TITLE_INDEX]));
        conditionsCheck.add(algorithmName(commandLines[CommandParserConstant.ALGORITHM_INDEX]));
        if (commandLines.length == 8) {
            conditionsCheck.add(outputName(commandLines[CommandParserConstant.OUTPUT_INDEX]));
            conditionsCheck.add(outputTitle(commandLines[CommandParserConstant.OUTPUT_TITLE_INDEX]));
        }
        List<Boolean> conditionError = conditionsCheck.stream().filter(check -> !check).toList();
        if (conditionError.isEmpty()) {
            log.info("Right command " + message);
            return message;
        } else {
            log.error("Wrong command " + message);
            return null;
        }
    }

    private Boolean outputName(String outputName) {
        return Arrays.stream(Output.values()).anyMatch((t) -> t.name().equals(outputName));
    }

    private Boolean outputTitle(String outputTitle) {
        return outputTitle.equals(CommandParserConstant.OUTPUT_TITLE);
    }

    private Boolean algorithmName(String algorithmName) {
        return Arrays.stream(AlgorithmName.values()).anyMatch((t) -> t.name().equals(algorithmName));

    }

    private Boolean algorithmTitle(String algorithmTitle) {
        return algorithmTitle.equals(CommandParserConstant.ALGORITHM_TITLE);
    }

    private Boolean periodName(String periodName) {
       return Arrays.stream(Period.values()).anyMatch((t) -> t.name().equals(periodName));
    }

    private Boolean currencyNames(String currencyNames) {
        Matcher matcherList = CURRENCY_NAMES.matcher(currencyNames);
        return matcherList.matches();
    }

    private Boolean periodTitle(String periodTitle) {
        return periodTitle.equals(CommandParserConstant.PERIOD_TITLE);
    }

    private Boolean operationName(String operationName) {
        return operationName.equals(CommandParserConstant.OPERATION_NAME);
    }

}
