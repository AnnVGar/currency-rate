package ru.ann.currencyrate.service;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.ann.currencyrate.common.ChartConstant;
import ru.ann.currencyrate.domain.CurrencyData;
import ru.ann.currencyrate.domain.type.AlgorithmName;
import ru.ann.currencyrate.domain.type.CurrencyName;
import ru.ann.currencyrate.domain.type.Output;
import ru.ann.currencyrate.domain.type.Period;
import ru.ann.currencyrate.util.LineChart;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class OutputService {
    public String saveCurrencyRateToGraph(List<CurrencyData> currencyDataList, String chartID) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (CurrencyData data : currencyDataList) {
            dataset.addValue(data.getUnitCurs(), data.getName(), data.getDate());
        }
        LineChart currencyGraph = new LineChart(ChartConstant.TITLE, dataset);
        currencyGraph.pack();
        currencyGraph.setVisible(true);
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        String fileName = ChartConstant.FILE_NAME + chartID + ChartConstant.FILE_EXTENSION;
        File file = new File(fileName);
        file.delete();
        try {
            ChartUtils.saveChartAsJPEG(file, currencyGraph.getChart(), ChartConstant.CHART_WIDTH, ChartConstant.CHART_HEIGHT, info);
            return fileName;
        } catch (IOException e) {
            log.error("Save file error", e);
        }
        return null;
    }

    public String rateToString(List<CurrencyData> list) {
        StringBuilder result = new StringBuilder();
        list.stream().sorted(Collections.reverseOrder()).forEach(currencyData -> result.append(currencyData.toString()).append("\n"));
        return result.toString();
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
