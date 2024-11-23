package com.sphy.pfc_app.view.refuels;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.refuels.RefuelListContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.presenter.Refuels.RefuelListPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class RefuelDetailsGrafByStationView extends BaseActivity implements RefuelListContract.View {

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

    private TextView username;
    private Button backButton;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consum_vehicle);

        sharedPreferencesManager = new SharedPreferencesManager(this);

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
        combinedChart = findViewById(R.id.combinedChart);
        username = findViewById(R.id.userNameTextView);
        backButton = findViewById(R.id.backButton);

        Intent intent = getIntent();
        String license = intent.getStringExtra("identifier");
        System.out.println("El nombre que se recoge en la gráfica por identifier es: " + license);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(license.toUpperCase());


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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMain(v);
            }
        });
    }


    @Override
    public void listRefuels(List<Refuel> refuels) {
        if (refuels != null && !refuels.isEmpty()) {
            System.out.println("Matrícula del primer Refuel recibido: " + refuels.get(0).getNameVehicle());
        }
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

        showData(refuels);
        // Contar la cantidad de repostajes por vehículo
        Map<String, Integer> vehicleRefuelCount = new HashMap<>();
        for (Refuel refuel : refuels) {
            String vehicleId = refuel.getNameVehicle();
            vehicleRefuelCount.put(vehicleId, vehicleRefuelCount.getOrDefault(vehicleId, 0) + 1);
        }

        // Preparar datos para las barras
        List<BarEntry> barEntries = new ArrayList<>();
        List<String> vehicleIds = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Integer> entry : vehicleRefuelCount.entrySet()) {
            barEntries.add(new BarEntry(index, entry.getValue()));
            vehicleIds.add(entry.getKey());
            index++;
        }

        // Crear dataset para barras
        BarDataSet barDataSet = new BarDataSet(barEntries, "Cantidad de repostajes");
        barDataSet.setColor(Color.BLUE);
        barDataSet.setValueTextSize(12f);

        // Configurar BarData para el CombinedChart
        BarData barData = new BarData(barDataSet);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);

        // Configuración del CombinedChart
        combinedChart.setData(combinedData);

        // Configurar el eje X para mostrar las matrículas
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value;
                return index >= 0 && index < vehicleIds.size() ? vehicleIds.get(index) : "";
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setTextSize(12f);
        // Añadir espacio extra en los extremos para evitar que las barras queden cortadas
        xAxis.setAxisMinimum(-0.5f); // Un poco antes de la primera barra
        xAxis.setAxisMaximum(barEntries.size() - 0.5f); // Un poco después de la última barra

        // Configurar el eje Y
        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        combinedChart.getAxisRight().setEnabled(false);

        // Configuración general del gráfico
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setDrawGridBackground(false);
        combinedChart.setDragEnabled(true);
        combinedChart.setScaleEnabled(true);

        // Actualizar el gráfico
        combinedChart.invalidate();
    }



    public void showData(List<Refuel> refuels) {
        // Establecer los títulos de las secciones
        tvTextRealConsum.setText("Vehículo con más repostajes:");
        tvTextBestConsum.setText("Combustibles repostados:");
        tvTextWorstConsum.setText("Fecha del último repostaje:");
        tvTextBestStation.setText("Dinero gastado en esta estación:");

        if (refuels == null || refuels.isEmpty()) {
            tvRealConsum.setText("Sin datos disponibles");
            tvBestConsum.setText("Sin datos disponibles");
            tvWorstConsum.setText("Sin datos disponibles");
            tvBestStation.setText("Sin datos disponibles");
            return;
        }

        // 1. Vehículo con más repostajes
        Map<String, Integer> vehicleRefuelCount = new HashMap<>();
        for (Refuel refuel : refuels) {
            String vehicleId = refuel.getNameVehicle();
            vehicleRefuelCount.put(vehicleId, vehicleRefuelCount.getOrDefault(vehicleId, 0) + 1);
        }

        String mostRefueledVehicle = null;
        int maxRefuelCount = 0;
        for (Map.Entry<String, Integer> entry : vehicleRefuelCount.entrySet()) {
            if (entry.getValue() > maxRefuelCount) {
                maxRefuelCount = entry.getValue();
                mostRefueledVehicle = entry.getKey();
            }
        }

        if (mostRefueledVehicle != null) {
            tvRealConsum.setText(mostRefueledVehicle);
        } else {
            tvRealConsum.setText("No hay datos de vehículos");
        }

        // 2. Combustibles repostados
        Set<String> fuelTypes = new HashSet<>();
        for (Refuel refuel : refuels) {
            if (refuel.getFuel() != null) {
                fuelTypes.add(refuel.getFuel());
            }
        }

        if (!fuelTypes.isEmpty()) {
            tvBestConsum.setText(String.join(", ", fuelTypes));
        } else {
            tvBestConsum.setText("No hay datos de combustibles");
        }

        // 3. Fecha del último repostaje
        Refuel lastRefuel = refuels.get(refuels.size() - 1);
        if (lastRefuel != null && lastRefuel.getCreationDate() != null) {
            tvWorstConsum.setText(lastRefuel.getCreationDate());
        } else {
            tvWorstConsum.setText("No hay datos de fecha");
        }

        // 4. Dinero gastado en esta estación
        float totalSpent = 0f;
        for (Refuel refuel : refuels) {
            totalSpent += refuel.getAmount();
        }

        tvBestStation.setText(String.format("%.2f €", totalSpent));
    }



    @Override
    public void showMessage(String message) {

    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}

