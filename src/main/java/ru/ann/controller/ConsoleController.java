package ru.ann.controller;

import ru.ann.algorithm.AlgorithmRate;
import ru.ann.domain.Command;
import ru.ann.domain.CurrencyName;
import ru.ann.domain.Period;
import ru.ann.service.CommandService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleController {

    private final Pattern RATE_COMMAND_PATTERN = Pattern.compile("RATE " + "(?:" + CurrencyName.valueForRegExp() + ") " + "(?:" + Period.valueForRegExp() + ")");
    private final CommandService commandService = new CommandService();

    /**
     * получение и выполнение допустимой команды из консоли
     */
    public void listenCommandFromConsole(AlgorithmRate algorithmRate) {
        Scanner console = new Scanner(System.in);
        System.out.println(сommandRules());
        while (console.hasNextLine()) {
            String commandLine = console.nextLine();
            if (commandLine.equals("exit")) {
                System.exit(0);
            }
            Matcher matcher = RATE_COMMAND_PATTERN.matcher(commandLine.toUpperCase());
            if (matcher.matches()) {
                String[] commandString = commandLine.split(" ");
                String currencyName = commandString[1];
                String period = commandString[2];
                Command command = new Command(currencyName, period, algorithmRate);
                commandService.setCommand(command);
                commandService.executeRate();
                printRate();
            } else {
                System.out.println("Wrong command");
            }
        }
    }


    /**
     * вывод прогноза на экран
     */
    private void printRate() {
        Command command = commandService.getCommand();
        int dayQuantity = command.getPeriod().getDayQuantity();
        command.getCurrencyDataList().stream()
                .sorted()
                .limit(dayQuantity)
                .forEach(System.out::println);
    }

    /**
     * получение правил ввода команды
     *
     * @return строка с правилами
     */
    public static String сommandRules() {
        return new StringBuilder("Write the command. What rate do you want? Print: \"rate %current  %period\"" + "\n")
                .append("Current values: ")
                .append(java.util.Arrays.asList(CurrencyName.values()))
                .append("\n")
                .append("Period values: ")
                .append(java.util.Arrays.asList(Period.values()))
                .toString();
    }
}
