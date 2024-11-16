package com.sphy.pfc_app.view.refuels;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.sphy.pfc_app.contract.refuels.RefuelListContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.presenter.Refuels.RefuelListPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class RefuelDetailsGrafByVehicleView extends BaseActivity implements RefuelListContract.View {

    private TextView tvDetalleDE;

    private TextView tvRealConsum;
    private TextView tvTextRealConsum;
    private TextView tvBestConsum;
    private TextView tvTextBestConsum;
    private TextView tvWorstConsum;
    private TextView tvTextWorstConsum;
    private TextView tvBestStation;
    private TextView tvTextBestStation;

    private ScrollView scrollView;

    private CombinedChart combinedChart;
    private RefuelListPresenter presenter;
    private Spinner filterSpinner;
    private List<Refuel> originalRefuels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consum_vehicle);
        scrollView = findViewById(R.id.scrollGraf);
        tvDetalleDE = findViewById(R.id.detalleDE);
        tvRealConsum = findViewById(R.id.realConsum);
        tvBestConsum = findViewById(R.id.bestConsum);
        tvWorstConsum = findViewById(R.id.worstConsum);
        tvBestStation = findViewById(R.id.bestStation);
        tvTextRealConsum = findViewById(R.id.textRealConsum);
        tvTextBestConsum = findViewById(R.id.textBestConsum);
        tvTextWorstConsum = findViewById(R.id.textWorstConsum);
        tvTextBestStation = findViewById(R.id.textBestStation);
        combinedChart = findViewById(R.id.combinedChart);
        filterSpinner = findViewById(R.id.filterSpinner);

        Intent intent = getIntent();
        String license = intent.getStringExtra("identifier");
        System.out.println("La matrícula que se recoge en la gráfica por identifier es: " + license);

        presenter = new RefuelListPresenter(this);
        presenter.findRefuelByIdentifier(license);
        setupFilterSpinner();
    }

    private void setupFilterSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"TODOS", "ÚLTIMOS 12 MESES", "ÚLTIMOS 6 MESES"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        // Listener del spinner
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFilter = parent.getItemAtPosition(position).toString();
                filterGraphData(selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void listRefuels(List<Refuel> refuels) {
        System.out.println("Matrícula del primer Refuel recibido: " + refuels.get(0).getNameVehicle());
        originalRefuels = new ArrayList<>(refuels);
        updateGraph(refuels);

    }

    private void filterGraphData(String filter) {
        if (originalRefuels == null || originalRefuels.isEmpty()) {
            return;
        }

        List<Refuel> filteredRefuels = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();

        switch (filter) {
            case "ÚLTIMOS 12 MESES":
                calendar.add(Calendar.YEAR, -1);
                long lastYearTime = calendar.getTimeInMillis();
                for (Refuel refuel : originalRefuels) {
                    if (isWithinRange(refuel.getCreationDate(), lastYearTime, currentTime)) {
                        filteredRefuels.add(refuel);
                    }
                }
                break;

            case "ÚLTIMOS 6 MESES":
                calendar.add(Calendar.MONTH, -6);
                long lastSixMonthsTime = calendar.getTimeInMillis();
                for (Refuel refuel : originalRefuels) {
                    if (isWithinRange(refuel.getCreationDate(), lastSixMonthsTime, currentTime)) {
                        filteredRefuels.add(refuel);
                    }
                }
                break;

            default: // "TODOS"
                filteredRefuels = originalRefuels;
                break;
        }

        // Actualiza la gráfica con los datos filtrados
        updateGraph(filteredRefuels);
    }

    private boolean isWithinRange(String dateString, long startTime, long endTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateString);
            long time = date.getTime();
            return time >= startTime && time <= endTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateGraph(List<Refuel> refuels) {
        List<BarEntry> barEntries = new ArrayList<>();
        List<Entry> lineEntries1 = new ArrayList<>();
        List<Entry> lineEntries2 = new ArrayList<>();

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

            if(i > 0){
                // Segunda línea: promedio de consumo
                float medConsumption = refuel.getMedConsumption();
                lineEntries2.add(new Entry(i, medConsumption));
            }
        }


        showData(refuels);

        BarDataSet barDataSet = new BarDataSet(barEntries, "Consumo del repostaje");
        barDataSet.setColor(Color.parseColor("#156082"));
        barDataSet.setValueTextSize(16f);

        LineDataSet lineDataSet1 = new LineDataSet(lineEntries1, "Consumo real");
        lineDataSet1.setColor(Color.GREEN);
        lineDataSet1.setCircleColor(Color.GREEN);
        lineDataSet1.setLineWidth(4f);
        lineDataSet1.setDrawValues(false);

        LineDataSet lineDataSet2 = new LineDataSet(lineEntries2, "Consumo Promedio");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setCircleColor(Color.RED);
        lineDataSet2.setLineWidth(4f);
        lineDataSet2.setDrawValues(false);

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
        // Añadir espacio extra en los extremos para evitar que las barras queden cortadas
        xAxis.setAxisMinimum(-0.5f); // Un poco antes de la primera barra
        xAxis.setAxisMaximum(barEntries.size() - 0.5f); // Un poco después de la última barra

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f);
        combinedChart.getAxisRight().setEnabled(false);
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setDragEnabled(true);
        combinedChart.setScaleEnabled(true);


        // Crear los objetos BarData y LineData
        BarData barData = new BarData(barDataSet);
        LineData lineData = new LineData(lineDataSet1, lineDataSet2);

        // Crear el gráfico combinado y añadir los conjuntos de datos
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);  // Añadir barras
        combinedData.setData(lineData); // Añadir las dos líneas

        combinedChart.setData(combinedData);
        combinedChart.invalidate();
    }


    public void showData(List<Refuel> refuels){

        List<Entry> lineEntries1 = new ArrayList<>();

        tvTextRealConsum.setText("Consumo real del vehículo:");
        tvTextBestConsum.setText("Fecha del repostaje con mejor consumo:");
        tvTextWorstConsum.setText("Fecha del repostaje con peor consumo:");
        tvTextBestStation.setText("Estación con el mejor consumo:");

        if (refuels == null || refuels.isEmpty()) {
            tvRealConsum.setText("Sin datos disponibles");
            tvBestConsum.setText("Sin datos disponibles");
            tvWorstConsum.setText("Sin datos disponibles");
            tvBestStation.setText("Sin datos disponibles");
            return;
        }

        // Variables para almacenar el mejor y peor repostaje
        Refuel bestRefuel = null;
        Refuel worstRefuel = null;

        // Encontrar el mejor y peor repostaje
        for (int i = 1; i < refuels.size(); i++) { // Empezamos en 1 porque usamos refuel-1
            Refuel current = refuels.get(i);

            // Comprobamos que tiene valores válidos
            if (current == null || current.getRefuelConsumption() == 0 || i - 1 < 0) continue;

            if (bestRefuel == null || current.getRefuelConsumption() < bestRefuel.getRefuelConsumption()) {
                bestRefuel = current;
            }

            if (worstRefuel == null || current.getRefuelConsumption() > worstRefuel.getRefuelConsumption()) {
                worstRefuel = current;
            }
        }

        // Mostrar el mejor repostaje
        if (bestRefuel != null) {
            Refuel previousRefuel = refuels.get(refuels.indexOf(bestRefuel) - 1);
            String bestText = String.format("%.2f", bestRefuel.getRefuelConsumption()) + " / " + previousRefuel.getCreationDate();
            tvBestConsum.setText(bestText);
        } else {
            tvBestConsum.setText("No hay datos para el mejor repostaje");
        }

        // Mostrar el peor repostaje
        if (worstRefuel != null) {
            Refuel previousRefuel = refuels.get(refuels.indexOf(worstRefuel) - 1);
            String worstText = String.format("%.2f", worstRefuel.getRefuelConsumption()) + " / " + previousRefuel.getCreationDate();
            tvWorstConsum.setText(worstText);
        } else {
            tvWorstConsum.setText("No hay datos para el peor repostaje");
        }

        // Mostrar el consumo real promedio (opcional)
        float totalConsumption = 0f; // Para almacenar la suma de los consumos reales
        int countRealConsumptions = 0; // Contador de repostajes reales

        for (int i = 0; i < refuels.size(); i++) {
            Refuel refuel = refuels.get(i);

            // Primera línea: consumo real
            if (refuel.isFulled() && i > 0 && refuels.get(i - 1).isFulled()) {
                float realConsumption = refuel.getRefuelConsumption();
                lineEntries1.add(new Entry(i, realConsumption));

                // Sumar el consumo real y contar el repostaje
                totalConsumption += realConsumption;
                countRealConsumptions++;
            }
        }


        float averageConsumption = countRealConsumptions > 0 ? totalConsumption / countRealConsumptions : 0f;
        tvRealConsum.setText(String.format("%.2f", averageConsumption));



        // Mejor estación (opcional, puedes ajustar según tus criterios)
        if (refuels == null || refuels.size() < 2) {
            tvBestStation.setText("Mejor estación: No disponible");
        } else {
            Refuel bestRefuel2 = null;
            int bestRefuelIndex = -1;
            float bestConsumption = Float.MAX_VALUE;

            // Buscar el repostaje con el mejor consumo
            for (int i = 1; i < refuels.size(); i++) {
                Refuel currentRefuel = refuels.get(i);
                if (currentRefuel.isFulled() && currentRefuel.getRefuelConsumption() < bestConsumption) {
                    bestConsumption = currentRefuel.getRefuelConsumption();
                    bestRefuel2 = currentRefuel;
                    bestRefuelIndex = i;
                }
            }

            // Buscar el nombre de la estación del repostaje anterior
            if (bestRefuel2 != null && bestRefuelIndex > 0) {
                Refuel previousRefuel = refuels.get(bestRefuelIndex - 1);
                tvBestStation.setText(previousRefuel.getNameStation());
            } else {
                tvBestStation.setText("Mejor estación: No disponible");
            }
        }
    }

    @Override
    public void showMessage(String message) {
        // Mostrar mensajes
    }
}

