package ru.ann.currencyrate.util;

import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import ru.ann.currencyrate.common.ChartConstant;

import java.awt.Dimension;
@Getter
public class LineChart extends ApplicationFrame {
    private CategoryDataset dataset;
    private final JFreeChart chart;
    private final ChartPanel chartPanel;

    public LineChart(final String title, CategoryDataset data) {
        super(title);
        dataset = data;
        chart = ChartFactory.createLineChart(title, ChartConstant.XLABLE, ChartConstant.YLABLE, dataset, PlotOrientation.VERTICAL, true, true, false);
        this.chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new Dimension(ChartConstant.PANEL_WIDTH , ChartConstant.PANEL_HEIGHT));
        setContentPane(chartPanel);
    }

}
