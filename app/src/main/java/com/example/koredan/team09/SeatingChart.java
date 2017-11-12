package com.example.koredan.team09;

/**
 * Created by Ben on 11/11/2017.
 */

public class SeatingChart {
    private String chartName;

    public SeatingChart(String chartName) {
        this.chartName = chartName;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    @Override
    public String toString() {
        return chartName;
    }
}
