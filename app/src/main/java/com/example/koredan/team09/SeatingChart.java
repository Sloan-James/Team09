package com.example.koredan.team09;

public class SeatingChart {
    private int id;
    private int col;
    private int row;
    private String chartName;

    public SeatingChart(String chartName, int id, int col, int row) {
        this.chartName = chartName;
        this.id = id;
        this.col = col;
        this.row = row;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColumns() { return col; }

    public int getRows() { return row; }

    @Override
    public String toString() {
        return chartName;
    }
}
