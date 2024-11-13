package com.sphy.pfc_app.view.refuels;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.refuels.RefuelDetailsContract;
import com.sphy.pfc_app.contract.refuels.RefuelListContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.presenter.Refuels.RefuelDetailsPresenter;
import com.sphy.pfc_app.presenter.Refuels.RefuelListPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class RefuelDetailsGrafView extends BaseActivity implements RefuelListContract.View {

    private TextView tvDetalleDE;
    private BarChart barChart;
    private RefuelListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consum_vehicle);
        tvDetalleDE = findViewById(R.id.detalleDE);

        Intent intent = getIntent();
        String license = intent.getStringExtra("license");

        presenter = new RefuelListPresenter(this);
        presenter.findRefuelByIdentifier(license);



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void listRefuels(List<Refuel> refuels) {
        List<BarEntry> barEntries = new ArrayList<>();
        List<Entry> lineEntries1 = new ArrayList<>(); // Primera línea
        List<Entry> lineEntries2 = new ArrayList<>(); // Segunda línea

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Collections.sort(refuels, (r1, r2) -> {
            try {
                Date date1 = dateFormat.parse(r1.getCreationDate());
                Date date2 = dateFormat.parse(r2.getCreationDate());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });

        // Iterar sobre los repostajes y agregar los datos en las listas correspondientes
        for (int i = 0; i < refuels.size(); i++) {
            Refuel refuel = refuels.get(i);

            // Datos para las barras
            float consumption = refuel.getRefuelConsumption();
            barEntries.add(new BarEntry(i, consumption));

            // Primera línea: consumo real
            if (refuel.isFulled() && i > 0 && refuels.get(i - 1).isFulled()) {
                float realConsumption = refuel.getRefuelConsumption();
                lineEntries1.add(new Entry(i, realConsumption));
            }

            // Segunda línea: promedio de consumo
            float medConsumption = refuel.getMedConsumption();
            lineEntries2.add(new Entry(i, medConsumption));
        }

        // Conjunto de datos para las barras
        BarDataSet barDataSet = new BarDataSet(barEntries, "Consumo del repostaje");
        barDataSet.setColor(Color.parseColor("#156082"));
        barDataSet.setValueTextSize(16f);

        // Conjunto de datos para la primera línea
        LineDataSet lineDataSet1 = new LineDataSet(lineEntries1, "Consumo real");
        lineDataSet1.setColor(Color.GREEN);
        lineDataSet1.setCircleColor(Color.GREEN);
        lineDataSet1.setLineWidth(4f);
        lineDataSet1.setDrawValues(false);

        // Conjunto de datos para la segunda línea
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries2, "Consumo Promedio");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setCircleColor(Color.RED);
        lineDataSet2.setLineWidth(4f);
        lineDataSet2.setDrawValues(false);

        // Crear los objetos BarData y LineData
        BarData barData = new BarData(barDataSet);
        LineData lineData = new LineData(lineDataSet1, lineDataSet2);

        // Crear el gráfico combinado y añadir los conjuntos de datos
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);  // Añadir barras
        combinedData.setData(lineData); // Añadir las dos líneas

        CombinedChart combinedChart = findViewById(R.id.combinedChart);
        combinedChart.setData(combinedData);
        combinedChart.invalidate();

        // Configuración del eje X para mostrar fechas
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value;
                if (index >= 0 && index < refuels.size()) {
                    try {
                        Date date = dateFormat.parse(refuels.get(index).getCreationDate());
                        return displayFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return "";
            }
        });

        // Configuración adicional
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setTextSize(14f);

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f);
        combinedChart.getAxisRight().setEnabled(false);
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setDragEnabled(true);
        combinedChart.setScaleEnabled(true);
    }


    @Override
    public void showMessage(String message) {

    }
}
