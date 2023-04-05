package ru.ann.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.ann.algorithm.AlgorithmRate;
import ru.ann.domain.*;
import ru.ann.service.CommandService;
import ru.ann.util.DateUtils;
import ru.ann.util.LineChart;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Getter
public class TelegramController {

    private final Pattern RATE_COMMAND_PATTERN_LIST = Pattern.compile("RATE " + "(" + CurrencyName.valueForRegExp() + ")"
            + " -DATE (" + Period.valueForRegExp() + "|\\d\\d.\\d\\d.\\d\\d\\d\\d)"
            + " -ALG (" + AlgorithmName.valueForRegExp() + ")"
            + "( -OUTPUT LIST){0,1}");
    private final Pattern RATE_COMMAND_PATTERN_GRAPH = Pattern.compile("RATE " + "(" + CurrencyName.valueForRegExp() + ")" +
            "(,(" + CurrencyName.valueForRegExp() + ")){0,4}"
            + " -DATE (" + Period.valueForRegExp() + ")"
            + " -ALG (" + AlgorithmName.valueForRegExp() + ")"
            + " -OUTPUT GRAPH");
    private CommandService commandService;

    /**
     * получение и выполнение допустимой команды из консоли
     */
    public String parseCommandFromLine(String message) {
        log.debug("Start to parse command: "+message);
        commandService = new CommandService();
        String commandLine = message.toUpperCase();
        Matcher matcherList = RATE_COMMAND_PATTERN_LIST.matcher(commandLine);
        Matcher matcherGraph = RATE_COMMAND_PATTERN_GRAPH.matcher(commandLine);
        if (matcherList.matches() || matcherGraph.matches()) {
            String[] commandArr = commandLine.split(" ");
            String[] currencyNameArr = commandArr[1].split(",");
            String period = parsePeriod(commandArr[3]);
            LocalDate startDate = parseStartDate(period, commandArr[3]);
            AlgorithmRate algorithmRate = parseAlgorithm(commandArr[5]);
            for (String name : currencyNameArr) {
                Command command;
                if (commandArr.length == 6) {
                    command = new Command(name, period, startDate, algorithmRate);
                } else {
                    String output = commandArr[7];
                    command = new Command(name, period, startDate, algorithmRate, output);
                }
                commandService.addCommand(command);
            }
            log.info("Success command parse");
            return ("OK");
        } else {
            log.error("Wrong command");
            return ("Wrong command");
        }
    }

    public String convertCurrencyRateToString() {
        return rateToString(commandService.executeRate());
    }

    public void saveCurrencyRateToGraph() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (CurrencyData data : commandService.executeRate()) {
            dataset.addValue(data.getValue(), data.getCdx(), data.getDate());
        }
        LineChart currencyGraph = new LineChart("Currency rate", dataset);
        currencyGraph.pack();
        currencyGraph.setVisible(true);
        final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        final File file = new File("Chart.jpeg");
        try {
            ChartUtils.saveChartAsJPEG(file, currencyGraph.getChart(), 600, 400, info);
        } catch (IOException e) {
            log.error("Save file error",e);
        }
    }


    private LocalDate parseStartDate(String period, String date) {
        LocalDate starDate = period.equals("DAY") ? LocalDate.parse(date, DateUtils.UPLOAD_FORMATTER) : LocalDate.now().plusDays(1);
        return starDate;
    }

    private AlgorithmRate parseAlgorithm(String algorithmName) {
        AlgorithmRate algorithm = null;
        try {
            algorithm = (AlgorithmRate) Class.forName(AlgorithmName.getFullAlgorithmClassName(algorithmName)).newInstance();
        } catch (ClassNotFoundException e) {
          log.error("No class for algorithm",e);
        } catch (InstantiationException e) {
            log.error("Instance algorithm error",e);
        } catch (IllegalAccessException e) {
            log.error("Illigal access error",e);
        }
        return algorithm;
    }

    private String parsePeriod(String period) {
        try {
            Enum.valueOf(Period.class, period);
            return period;
        } catch (IllegalArgumentException e) {
            return "DAY";
        }
    }


    public String rateToString(List<CurrencyData> list) {
        StringBuilder result = new StringBuilder();
        list.stream().sorted().forEach(currencyData -> result.append(currencyData.toString()).append("\n"));
        return result.toString();
    }


    public String сommandRules() {
        return new StringBuilder("Write the command(case - insensitive). What rate do you want? Print:")
                .append("\n").append("\"rate %current -date %period -alg %algorithm -output %output\"" + "\n")
                .append("Current values: ").append(java.util.Arrays.asList(CurrencyName.values())).append("\n")
                .append("For graph several currencies separated by commas are possible.")
                .append("Period values: ").append(java.util.Arrays.asList(Period.values()))
                .append(" or concrete date dd.mm.yyyy").append("\n").append("Algorithm values: ")
                .append(java.util.Arrays.asList(AlgorithmName.values())).append("\n").append("Output values: ")
                .append(java.util.Arrays.asList(Output.values())).toString();
    }
}
