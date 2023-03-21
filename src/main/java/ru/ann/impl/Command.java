package ru.ann.impl;

import ru.ann.core.AlgorithmRate;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

public class Command {
    private final CurrencyName currencyName;
    private final Period period;
    private final LinkedList<CurrencyData> currencyDataList = new LinkedList<>();
    private final AlgorithmRate algorithmRate;

    public Command(String currencyName, String period, AlgorithmRate algorithmRate) {
        this.currencyName = CurrencyName.valueOf(currencyName.toUpperCase(Locale.ROOT));
        this.period = Period.valueOf(period.toUpperCase(Locale.ROOT));
        this.algorithmRate = algorithmRate;
    }


    public void execute() {
        fillCurrencyDataList();
        for (int i = 0; i < period.getDayQuantity(); i++) {
            addRateELement();
        }
        printRate(period.getDayQuantity());
    }

    /**
     * вывод прогноза на экран
     *
     * @param dayQuantity период прогнозирования
     */
    private void printRate(int dayQuantity) {
        currencyDataList.stream().limit(dayQuantity).forEach(System.out::println);
    }

    /**
     * Добавляем  прогнозируемый элемент в currencyDataList
     * поле элемента value расчитываем фунуцией algorithmRate.getNextValue()
     */
    private void addRateELement() {
        CurrencyData previous = currencyDataList.get(0);
        CurrencyData newData = new CurrencyData();
        newData.setDate(previous.getDate().isAfter(LocalDate.now()) ? previous.getDate().plusDays(1) : LocalDate.now().plusDays(1));
        newData.setCdx(previous.getCdx());
        newData.setNominal(previous.getNominal());
        double value = algorithmRate.getNextValue(currencyDataList);
        newData.setValue(value);
        newData.setCurs(value * previous.getNominal());
        currencyDataList.addFirst(newData);
    }


    /**
     * заполнение списка данных о курсе валют
     * из файла
     */
    private void fillCurrencyDataList() {
        File file = new File(System.getProperty("user.dir") + File.separator + currencyName.name().toLowerCase() + ".csv");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            for (int i = 0; i < 7; i++) {
                String[] dataLine = br.readLine().split(";");
                currencyDataList.add(new CurrencyData(dataLine[0], dataLine[1], dataLine[2], dataLine[3]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * получение допустимой команды из консоли
     *
     * @return полученную команду
     */
    public static Command getCommand(AlgorithmRate algorithmRate) {
        System.out.println(getCommandRules());
        Scanner console = new Scanner(System.in);
        while (true) {
            String[] command = console.nextLine().split(" ");
            if (command.length == 3) {
                if (Arrays.stream(CurrencyName.values()).anyMatch((x) -> x.name().equals(command[1].toUpperCase()))
                        && Arrays.stream(Period.values()).anyMatch((x) -> x.name().equals(command[2].toUpperCase()))) {
                    return new Command(command[1], command[2], algorithmRate);
                }
            }
            System.out.println("Wrong command");
            System.out.println(getCommandRules());
        }
    }

    /**
     * получение правил ввода команды
     *
     * @return строка с правилами
     */
    public static String getCommandRules() {
        StringBuilder rules = new StringBuilder("Write the command. What rate do you want? Print: \"rate %current  %period\"" + "\n")
                .append("Current values: ")
                .append(java.util.Arrays.asList(CurrencyName.values()))
                .append("\n")
                .append("Period values: ")
                .append(java.util.Arrays.asList(Period.values()))
                .append("\n");
        return rules.toString();
    }
}
