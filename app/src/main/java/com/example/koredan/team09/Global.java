package com.example.koredan.team09;

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
}
