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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seating_chart);
        int id = getIntent().getIntExtra("Chart_ID", -1);
        SeatingChart chart = Global.getChartById(id);
        setTitle(chart.getChartName());
        GridLayout gLS = (GridLayout) findViewById(R.id.gridLayoutSeats);
        gLS.setColumnCount(chart.getColumns());
        gLS.setRowCount(chart.getRows());
        int total = chart.getColumns() * chart.getRows();
        for (int i = 0; i < total; i++) {
            final Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
            btnTag.setText("Seat " + (i + 1));
            btnTag.setId(i);
            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click(btnTag.getId());
                }
            });
            gLS.addView(btnTag);
        }
    }

    public void click(final int id) {

        Log.d("Button ID: ", "" + id);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Seat " + (id + 1));
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Button btn = (Button) findViewById(id);
                btn.setText(input.getText().toString());

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
