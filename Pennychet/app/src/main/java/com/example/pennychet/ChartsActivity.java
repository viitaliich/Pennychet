package com.example.pennychet;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: @@@ ADD CREATED");
        setContentView(R.layout.charts_screen);

        // PieChart
        PieChart pieChart;
        PieDataSet pieDataSet;

        String[] categories = getResources().getStringArray(R.array.categories);
        ArrayList<PieEntry> pieChartCtg = new ArrayList<>();
        pieChartCtg.add(new PieEntry(1, categories[0]));
        pieChartCtg.add(new PieEntry(2, categories[1]));
        pieChartCtg.add(new PieEntry(3, categories[2]));
        pieChartCtg.add(new PieEntry(4, categories[3]));
        pieChartCtg.add(new PieEntry(5, categories[4]));
        pieChartCtg.add(new PieEntry(6, categories[5]));
        pieChartCtg.add(new PieEntry(7, categories[6]));
        pieChartCtg.add(new PieEntry(8, categories[7]));
        pieChartCtg.add(new PieEntry(9, categories[8]));
        pieChartCtg.add(new PieEntry(10, categories[9]));
        pieChartCtg.add(new PieEntry(11, categories[10]));
        pieChartCtg.add(new PieEntry(12, categories[11]));

        pieChart = findViewById(R.id.pieChart);

        int[] colorClassArray = new int[]{
                getResources().getColor(R.color.ctgT1),
                getResources().getColor(R.color.ctgT2),
                getResources().getColor(R.color.ctgT3),
                getResources().getColor(R.color.ctgT4),
                getResources().getColor(R.color.ctgL1),
                getResources().getColor(R.color.ctgL2),
                getResources().getColor(R.color.ctgR1),
                getResources().getColor(R.color.ctgR2),
                getResources().getColor(R.color.ctgB1),
                getResources().getColor(R.color.ctgB2),
                getResources().getColor(R.color.ctgB3),
                getResources().getColor(R.color.ctgB4),
        };

        pieDataSet = new PieDataSet(pieChartCtg, "");
        pieDataSet.setColors(colorClassArray);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawRoundedSlices(false);
        pieChart.setUsePercentValues(false);
        pieChart.setHoleRadius(65);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setWordWrapEnabled(true);
        Description description = pieChart.getDescription();
        description.setEnabled(false);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setSliceSpace(1);
        pieChart.setDrawEntryLabels(false);
        pieChart.animateX(400);
        pieChart.invalidate();

        // BarChart

        int[] barColorClassArray = new int[]{
                getResources().getColor(R.color.ctgT1),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgT2),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgT3),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgT4),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgL1),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgL2),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgR1),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgR2),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgB1),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgB2),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgB3),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgB4),
                getResources().getColor(R.color.goal),
        };

        BarChart barChart;
        BarDataSet barDataSet;

        ArrayList<BarEntry> barChartCtg = new ArrayList<>();
        barChartCtg.add(new BarEntry(1, new float[] {1, 10}, "label 1"));
        barChartCtg.add(new BarEntry(2, new float[] {2, 20}, "label 2"));
        barChartCtg.add(new BarEntry(3, new float[] {3, 30}, "label 3"));
        barChartCtg.add(new BarEntry(4, new float[] {4, 40}, "label 3"));
        barChartCtg.add(new BarEntry(5, new float[] {5, 50}, "label 3"));
        barChartCtg.add(new BarEntry(6, new float[] {6, 60}, "label 3"));
        barChartCtg.add(new BarEntry(7, new float[] {7, 70}));
        barChartCtg.add(new BarEntry(8, new float[] {8, 80}));
        barChartCtg.add(new BarEntry(9, new float[] {9, 90}));
        barChartCtg.add(new BarEntry(10, new float[] {10, 100}));
        barChartCtg.add(new BarEntry(11, new float[] {0, 110}));
        barChartCtg.add(new BarEntry(12, new float[] {12, 120}));

        barChart = findViewById(R.id.barChart);

        barDataSet = new BarDataSet(barChartCtg, "");
        barDataSet.setColors(barColorClassArray);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f); // set custom bar width
        barChart.setData(barData);
//        barChart.setFitBars(true);
        Legend barLegend = barChart.getLegend();
        barLegend.setEnabled(false);
        barLegend.setWordWrapEnabled(true);
        Description barDescription = barChart.getDescription();
        barDescription.setEnabled(false);
        barDataSet.setValueTextColor(Color.BLACK);
        String[] dates = new String[] {
                "04.12",
                "05.12",
                "06.12",
                "07.12",
                "08.12",
                "09.12",
                "10.12",
                "11.12" };
        barDataSet.setValueTextSize(10);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(categories));
        barChart.getXAxis().setEnabled(false);
        barChart.animateX(750);
        barChart.animateY(750);
        barChart.invalidate();


        // LineChart

        int[] lineColorClassArray = new int[]{
                getResources().getColor(R.color.ctgT1),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgT2),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgT3),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgT4),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgL1),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgL2),
                getResources().getColor(R.color.goal),
                getResources().getColor(R.color.ctgR1),
                getResources().getColor(R.color.goal),
        };

        LineChart lineChart;
        LineDataSet lineDataSet;
        lineChart = findViewById(R.id.lineChart);


        ArrayList<Entry> lineChartCtg = new ArrayList<>();
        lineChartCtg.add(new Entry(1, 1, "label 1"));
        lineChartCtg.add(new Entry(2, 2, "label 2"));
        lineChartCtg.add(new Entry(3, 3, "label 3"));
        lineChartCtg.add(new Entry(4, 4, "label 3"));
        lineChartCtg.add(new Entry(5, 5, "label 3"));
        lineChartCtg.add(new Entry(6, 6, "label 3"));
        lineChartCtg.add(new Entry(7, 7));


        lineDataSet = new LineDataSet(lineChartCtg, "");
//        lineDataSet.setColors(lineColorClassArray);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setCircleColor(Color.BLUE);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
//        Legend lineLegend = lineChart.getLegend();
//        lineLegend.setEnabled(true);
//        lineLegend.setWordWrapEnabled(true);
        Description lineDescription = lineChart.getDescription();
        lineDescription.setEnabled(false);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setLineWidth(1);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dates));
//        lineChart.getXAxis().setEnabled(false);
        lineChart.animateX(750);
        lineChart.animateY(750);
        lineChart.invalidate();


        Button btnChartsCancel = findViewById(R.id.btnChartsCancel);
        btnChartsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

