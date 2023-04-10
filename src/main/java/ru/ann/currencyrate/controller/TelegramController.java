package ru.ann.currencyrate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.ann.currencyrate.common.ChartConstant;
import ru.ann.currencyrate.domain.Command;
import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.AlgorithmName;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.domain.type.Output;
import ru.ann.currencyrate.domain.type.Period;
import ru.ann.currencyrate.service.CommandService;
import ru.ann.currencyrate.service.CommandParserService;
import ru.ann.currencyrate.service.algorithm.OutputService;
import ru.ann.currencyrate.util.LineChart;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
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
    private CommandParserService parserCommandService = new CommandParserService();
    private final OutputService outputService = new OutputService();

    /**
     * получение допустимой  команды из консоли
     */
    public String parseCommandFromLine(String message) {
        log.debug("Start to parse command: " + message);
        commandService = new CommandService();
        String commandLine = message.toUpperCase();
        Matcher matcherList = RATE_COMMAND_PATTERN_LIST.matcher(commandLine);
        Matcher matcherGraph = RATE_COMMAND_PATTERN_GRAPH.matcher(commandLine);
        if (matcherList.matches() || matcherGraph.matches()) {
            for (Command command: parserCommandService.parserCommandFromLine(message)){
                commandService.addCommand(command);
            }
            log.info("Success command parse");
            return ("OK");
        } else {
            log.error("Wrong command");
            return ("Wrong command");
        }
    }

    public List<CurrencyData> executeRate(){
        return commandService.executeRate();
    }

    public String rateToString(){
        return outputService.rateToString(executeRate());
    }

    public String rateToJPEG(String chartId){
       return outputService.saveCurrencyRateToGraph(executeRate(),chartId);
    }


    public String commandRules() {
        return "Write the command(case - insensitive). What rate do you want? Print:" + "\n"
                + "\"rate %current -date %period -alg %algorithm -output %output\"" + "\n"
                + "Current values: " + java.util.Arrays.asList(CurrencyName.values()) + "\n"
                + "For graph several currencies separated by commas are possible." + "\n"
                + "Period values: " + java.util.Arrays.asList(Period.values()) + " or concrete date dd.mm.yyyy" + "\n"
                + "Algorithm values: " + java.util.Arrays.asList(AlgorithmName.values()) + "\n"
                + "Output values: " + java.util.Arrays.asList(Output.values());
    }
}
