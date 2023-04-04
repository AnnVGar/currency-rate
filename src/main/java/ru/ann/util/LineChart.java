package ru.ann.util;

import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;

import java.awt.*;
@Getter
public class LineChart extends ApplicationFrame {
    final CategoryDataset dataset;
    final JFreeChart chart;
    final ChartPanel chartPanel;

    public LineChart(final String title, CategoryDataset dataset) {
        super(title);
        this.dataset = dataset;
        this.chart = ChartFactory.createLineChart(title, "date", "value",
                dataset, PlotOrientation.VERTICAL, true, true, false
        );
        this.chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new Dimension(560, 480));
        setContentPane(chartPanel);
    }

}
