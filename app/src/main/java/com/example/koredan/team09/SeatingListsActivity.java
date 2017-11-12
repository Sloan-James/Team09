package com.example.koredan.team09;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SeatingListsActivity extends AppCompatActivity {

    private ArrayList<String> listNames = new ArrayList<String>();
    private ArrayList<SeatingChart> charts = new ArrayList<SeatingChart>();
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

    public void loadCharts() {
        SharedPreferences prefs = getSharedPreferences("Charts", MODE_PRIVATE);
        String restoredText = prefs.getString("charts", null);
        if (restoredText != null) {
            String chartNames = prefs.getString("charts", "n/a");//"n/a" is the default value.
            if (!chartNames.equalsIgnoreCase("n/a")) {
                listNames = new ArrayList<String>(Arrays.asList(chartNames.split(":")));
            }
        }
    }

    public void deleteChart(int position) {
        listNames.remove(position);
        saveCharts();
    }

    public void addChart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Enter Seating Chart Name");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().toUpperCase();//gets input
                if (name != null && name.length() > 0 && !listNames.contains(name)) {
                    SeatingChart sc = new SeatingChart(name);
                    listNames.add(sc.getChartName());
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

    public void saveCharts() {
        SharedPreferences.Editor editor = getSharedPreferences("Charts", MODE_PRIVATE).edit();
        String chartNames = "";
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
        editor.putString("charts", chartNames);
        editor.apply();
    }

}
