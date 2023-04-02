package ru.ann.controller;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.ann.algorithm.AlgorithmRate;
import ru.ann.domain.*;
import ru.ann.service.CommandService;
import ru.ann.util.DateUtils;

@Slf4j
public class TelegramController {

    private final Pattern RATE_COMMAND_PATTERN = Pattern.compile("RATE " + "(?:" + CurrencyName.valueForRegExp() + ")"
            + " -DATE (?:" + Period.valueForRegExp() + "|\\d\\d.\\d\\d.\\d\\d\\d\\d)"
            + " -ALG (?:" + AlgorithmName.valueForRegExp() + ")"
            + " -OUTPUT (?:" + Output.valueForRegExp() + ")");
    private final CommandService commandService = new CommandService();

    /**
     * получение и выполнение допустимой команды из консоли
     */
    public void parseCommandFromLine(String message) {
        String commandLine = message.toUpperCase();
        System.out.println(RATE_COMMAND_PATTERN);
        Matcher matcher = RATE_COMMAND_PATTERN.matcher(commandLine);
        if (matcher.matches()) {
            String[] commandString = commandLine.split(" ");
            String currencyName = commandString[1];
            String period = parsePeriod(commandString[3]);
            LocalDate startDate = parseStartDate(period,commandString[3]);
            AlgorithmRate algorithmRate = parseAlgorithm(commandString[5]);
            Command command = new Command(currencyName, period, startDate, algorithmRate);
            System.out.println(command);
            commandService.setCommand(command);
            commandService.executeRate();
            printRate();
        } else {
            log.error("Wrong command");
        }
    }

    private LocalDate parseStartDate(String period,String date) {
        LocalDate starDate = period.equals("DAY")?LocalDate.parse(date, DateUtils.UPLOAD_FORMATTER):LocalDate.now().plusDays(1);
        return starDate;
    }

    private AlgorithmRate parseAlgorithm(String algorithmName) {
        return  AlgorithmName.valueOf(algorithmName).getAlgorithmRate();
    }

    private String parsePeriod(String period) {
        try {
            Enum.valueOf(Period.class, period);
            return period;
        } catch (IllegalArgumentException e) {
            return "DAY";
        }
    }



    /**
     * вывод прогноза на экран
     */
    public String printRate() {
        StringBuilder result = new StringBuilder();
        Command command = commandService.getCommand();
        int dayQuantity = command.getPeriod().getDayQuantity();
        command.getCurrencyDataList().stream()
                .sorted()
                .limit(dayQuantity)
                .forEach(currencyData -> result.append(currencyData.toString()).append("\n"));
        return result.toString();
    }

    /**
     * получение правил ввода команды
     *
     * @return строка с правилами
     */
    public String сommandRules() {
        return new StringBuilder("Write the command(case - insensitive). What rate do you want? Print:")
                .append("\n")
                .append("\"rate %current -date %period -alg %algorithm -output %output\"" + "\n")
                .append("Current values: ")
                .append(java.util.Arrays.asList(CurrencyName.values()))
                .append("\n")
                .append("Period values: ")
                .append(java.util.Arrays.asList(Period.values()))
                .append(" or concrete date dd.mm.yyyy")
                .append("\n")
                .append("Algorithm values: ")
                .append(java.util.Arrays.asList(AlgorithmName.values()))
                .append("\n")
                .append("Output values: ")
                .append(java.util.Arrays.asList(Output.values()))
                .toString();
    }
}
