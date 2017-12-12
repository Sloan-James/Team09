package com.example.koredan.team09;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SeatingChartActivity extends AppCompatActivity {
    private int chartId;
    private GridLayout gLS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seating_chart);
        chartId = getIntent().getIntExtra("Chart_ID", -1);
        SeatingChart chart = Global.getChartById(chartId);
        setTitle(chart.getChartName());
        gLS = (GridLayout) findViewById(R.id.gridLayoutSeats);
        gLS.setColumnCount(chart.getColumns());
        gLS.setRowCount(chart.getRows());
        int total = chart.getSize();//chart.getColumns() * chart.getRows() + chart.getExtraSeats();
        for (int i = 0; i < total; i++) {
            final Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
            if (chart.getNameFromSeat(i) == null || chart.getNameFromSeat(i).equals(""))
                btnTag.setText("Chair " + (i + 1));
            else
                btnTag.setText(chart.getNameFromSeat(i));
            btnTag.setId(i);
            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click(btnTag.getId(), btnTag);
                }
            });
            gLS.addView(btnTag);
        }
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSeat();
            }
        });
        Button btnDimension = (Button) findViewById(R.id.btnDimension);
        btnDimension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDimension();
            }
        });
    }

    public void changeDimension() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dimension");
        builder.setMessage("Note: New dimension cannot be smaller than current dimension.");
        final EditText input = new EditText(this);
        builder.setView(input);
        input.setHint("3x3");
        final AppCompatActivity app = this;
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String dimension[] = (input.getText().toString().toLowerCase()).split("x");
                if (dimension.length >= 2) {
                    try {
                        SeatingChart c = Global.charts.get(chartId);
                        c.setColumns(Integer.parseInt(dimension[0]));
                        c.setRows(Integer.parseInt(dimension[1]));
                        gLS.setColumnCount(c.getColumns());
                        gLS.setRowCount(c.getRows());
                        Global.charts.set(chartId, c);
                        Global.saveCharts(app);
                    } catch (Exception e) {
                        Toast.makeText(SeatingChartActivity.this, "I told you, it cannot be smaller than the current dimension!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = input.getText().toString();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addSeat() {
        Global.charts.get(chartId).addSeat();
        final Button btnTag = new Button(this);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        int id = Global.charts.get(chartId).getSize() - 1;
        btnTag.setText(Global.charts.get(chartId).getNameFromSeat(id));
        btnTag.setId(id);
        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(btnTag.getId(), btnTag);
            }
        });
        gLS.addView(btnTag);
        Global.saveCharts(this);
    }

    public void click(final int id, final Button button) {

        Log.d("Button ID: ", "" + id);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Global.charts.get(chartId).getNameFromSeat(id));
        final EditText input = new EditText(this);
        builder.setView(input);
        final AppCompatActivity app = this;
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Button btn = (Button) findViewById(id);
                String text = input.getText().toString();
                btn.setText(text);
                Log.wtf("Seat ID:", which + "");
                Global.setSeatNameByChart(chartId, id, text);
                Global.saveCharts(app);
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.wtf("IDS", "W: " + which + " : ID: " + id);
                SeatingChart c = Global.charts.get(chartId);
                int total =  Global.charts.get(chartId).getSize();
                c.removeSeat(id);
                for (int i = id + 1; i < total; i++) {
                    Button btn = (Button) findViewById(i);
                    btn.setId(btn.getId() - 1);
                }
                Global.charts.set(chartId, c);
                gLS.removeView(button);
                Global.saveCharts(app);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = input.getText().toString();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
