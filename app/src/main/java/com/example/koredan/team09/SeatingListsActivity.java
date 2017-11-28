package com.example.koredan.team09;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class SeatingListsActivity extends AppCompatActivity {

    private ArrayList<String> listNames = new ArrayList<String>();
    private ListView lView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seating_lists);
        loadCharts();
        ChartItemAdapter adapter = new ChartItemAdapter(listNames, this);
        lView = (ListView) findViewById(R.id.lvSeatCharts);
        lView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChart();
            }
        });
    }

    public void deleteChart(int position) {
        listNames.remove(position);
        Global.charts.remove(position);
        saveCharts();
    }

    public void viewChart(int position) {
        Intent intent = new Intent(getBaseContext(), SeatingChartActivity.class);
        intent.putExtra("Chart_ID", Global.charts.get(position).getId());
        startActivity(intent);
    }

    public void addChart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Enter Seating Chart Name");
        // Set up the input
        LinearLayout layout = new LinearLayout(getBaseContext());
        LinearLayout layoutLeft = new LinearLayout(layout.getContext());
        LinearLayout layoutRight = new LinearLayout(layout.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView f1 = new TextView(layoutLeft.getContext());
        f1.setText("Name:");
        TextView f2 = new TextView(layoutLeft.getContext());
        f2.setText("Dimension:");
        final EditText input = new EditText(this);
        final EditText input2 = new EditText(this);
        input.setHint("Computer Science");
        input2.setHint("3x3");
        layout.addView(layoutLeft);
        layout.addView(layoutRight);
        layoutLeft.addView(f1);
        layoutLeft.addView(input);
        layoutRight.addView(f2);
        layoutRight.addView(input2);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().toUpperCase();//gets input
                String dimension[] = (input2.getText().toString().toLowerCase()).split("x");
                if (name != null && name.length() > 0 && !listNames.contains(name) && dimension.length >= 2) {
                    try {
                        SeatingChart sc = new SeatingChart(name, Global.getFreeSlot(), Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1]));
                        listNames.add(sc.getChartName());
                        Global.charts.add(sc);
                    }catch (Exception e) {
                        Log.wtf("well", dimension[0] + " : " + dimension[1]);
                    }
                    lView.invalidateViews();
                    Toast.makeText(SeatingListsActivity.this, "Added new seating chart", Toast.LENGTH_LONG).show();
                    //Snackbar.make(view, "Added new seating chart", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    saveCharts();
                } else if (listNames.contains(name)) {
                    Toast.makeText(SeatingListsActivity.this, "You already have a seating chart for " + name, Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void loadCharts() {
        //saveCharts();//enable to wipe save
        SharedPreferences prefs = getSharedPreferences("Charts", MODE_PRIVATE);
        String chartNames = prefs.getString("charts", "n/a");//"n/a" is the default value.
        if (!chartNames.equalsIgnoreCase("n/a")) {
            Gson gson = new Gson();
            ArrayList<String> chartArray = new ArrayList<String>(Arrays.asList(chartNames.split("/")));
            for (String chartString : chartArray) {
                SeatingChart c = gson.fromJson(chartString, SeatingChart.class);
                if (c != null) {
                    //Log.wtf("Error", "ID: " + c.getId());
                    listNames.add(c.getChartName());
                    Global.charts.add(c);
                }
            }
            //listNames = new ArrayList<String>(Arrays.asList(chartNames.split(":")));
        }
    }

    public void saveCharts() {
        Gson gson = new Gson();
        String chartNames = "";
        for (int i = 0; i < Global.charts.size(); i++) {
            if (i == Global.charts.size() - 1)
                chartNames += gson.toJson(Global.charts.get(i));
            else
                chartNames += gson.toJson(Global.charts.get(i)) + "/";
        }
        SharedPreferences.Editor editor = getSharedPreferences("Charts", MODE_PRIVATE).edit();
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
