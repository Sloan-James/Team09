package com.example.koredan.team09;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Life on 11/28/2017.
 */

public class Global {

    public static ArrayList<SeatingChart> charts = new ArrayList<SeatingChart>();


    public static SeatingChart getChartById(int id) {
        for (SeatingChart c : charts)
            if (c.getId() == id)
                return c;
        return null;
    }

    public static int getFreeSlot() {
        for (int i = 0; i < charts.size() + 1; i++)
            if (!chartsContainsId(i))
                return i;
        return -1;
    }

    public static boolean chartsContainsId(int id) {
        for (SeatingChart chart : charts) {
            if (chart.getId() == id)
                return true;
        }
        return false;
    }

    public static void setSeatNameByChart(int chartId, int seatId, String name) {
        SeatingChart chart = charts.get(chartId);
        chart.setNameToSeat(seatId, name);
        charts.set(chartId, chart);
    }

    public static void saveCharts(AppCompatActivity app) {
        Gson gson = new Gson();
        String chartNames = "";
        for (int i = 0; i < charts.size(); i++) {
            if (i == charts.size() - 1)
                chartNames += gson.toJson(charts.get(i));
            else
                chartNames += gson.toJson(charts.get(i)) + "/";
        }
        SharedPreferences.Editor editor = app.getSharedPreferences("Charts", app.MODE_PRIVATE).edit();
        /*String chartNames = "";
        if (listNames.size() == 0)
            chartNames = "n/a";
        else {
            for (int i = 0; i < listNames.size(); i++) {
                if (i == listNames.size() - 1)
                    chartNames += listNames.get(i);
                else
                    chartNames += listNames.get(i) + ":";
            }
        }
        editor.putString("charts", chartNames);*/
        editor.putString("charts", chartNames);
        editor.apply();
    }
}
