package com.example.koredan.team09;

import java.util.ArrayList;

public class SeatingChart {
    private int id;
    private int col;
    private int row;
    private String chartName;
    private ArrayList<String> names;
    private int extraSeats;

    public SeatingChart(String chartName, int id, int col, int row) {
        this.chartName = chartName;
        this.id = id;
        this.col = col;
        this.row = row;
        int total = row * col;
        names = new ArrayList<String>(total);
        for (int i = 0; i < total; i++)
            names.add(i, "Empty Seat");
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

    public void setColumns(int col) { this.col = col; }

    public int getColumns() { return col; }

    public void setRows(int row) { this.row = row; }

    public int getRows() { return row; }

    public void setExtraSeats(int extraSeats) { this.extraSeats = extraSeats; }

    public int getExtraSeats() { return extraSeats; }

    public void addSeat() {
        extraSeats++;
        names.add("Empty Seat");
    }

    public void removeSeat(int index) {
        if (index >= names.size() || index < 0)
            return;
        names.remove(index);
    }

    public void setNameToSeat(int index, String name) {
        if (index >= names.size() || index < 0)
            return;
        names.set(index, name);
    }

    public String getNameFromSeat(int index) {
        if (index >= names.size() || index < 0)
            return "";
        return names.get(index);
    }

    public int getSize() {
        return names.size();
    }

    @Override
    public String toString() {
        return chartName;
    }
}
