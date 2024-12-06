package com.sphy.pfc_app.view.refuels;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

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
import java.util.List;
import java.util.stream.Collectors;


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

    private TextView tvDetalleDE2;

    private TextView tvRealConsum2;
    private TextView tvTextRealConsum2;
    private TextView tvBestConsum2;
    private TextView tvTextBestConsum2;
    private TextView tvWorstConsum2;
    private TextView tvTextWorstConsum2;
    private TextView tvBestStation2;
    private TextView tvTextBestStation2;

    private ScrollView scrollView;

    private CombinedChart combinedChart;
    private CombinedChart combinedChart2;
    private RefuelListPresenter presenter;
    private Spinner filterSpinner;
    private Spinner filterSpinner2;
    private List<Refuel> originalRefuels;
    private List<Refuel> filteredRefuelsByFuel1;
    private List<Refuel> filteredRefuelsByFuel2;

    private TextView username;
    private Button backButton;

    private float realConsumTotal = 0;
    private int realEntries = 0;
    private float realConsumTotal2 = 0;
    private int realEntries2 = 0;

    private float realConsum;
    private float realConsum2;
    private ImageButton menuButton;

    float maxRefuelConsumption1 = Float.MIN_VALUE;
    float maxRefuelConsumption2 = Float.MIN_VALUE;

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

        tvDetalleDE2 = findViewById(R.id.detalleDE2);
        tvRealConsum2 = findViewById(R.id.realConsum2);
        tvBestConsum2 = findViewById(R.id.bestConsum2);
        tvWorstConsum2 = findViewById(R.id.worstConsum2);
        tvBestStation2 = findViewById(R.id.bestStation2);
        tvTextRealConsum2 = findViewById(R.id.textRealConsum2);
        tvTextBestConsum2 = findViewById(R.id.textBestConsum2);
        tvTextWorstConsum2 = findViewById(R.id.textWorstConsum2);
        tvTextBestStation2 = findViewById(R.id.textBestStation2);
        combinedChart2 = findViewById(R.id.combinedChart2);

        username = findViewById(R.id.userNameTextView);
        backButton = findViewById(R.id.backButton);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        Intent intent = getIntent();
        String license = intent.getStringExtra("identifier");
        System.out.println("La matrícula que se recoge en la gráfica por identifier es: " + license);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(license);

        presenter = new RefuelListPresenter(this);
        presenter.findRefuelByIdentifier(license);
        setupFilterSpinner();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMain(v);
            }
        });
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

        originalRefuels = new ArrayList<>(refuels);

        filteredRefuelsByFuel1 = originalRefuels.stream()
                .filter(refuel -> refuel.getFuel() != null)
                .collect(Collectors.toList());
        if (!filteredRefuelsByFuel1.isEmpty()) {
            System.out.println("Matrícula del primer Refuel recibido: " + filteredRefuelsByFuel1.get(0).getNameVehicle());
        } else {
            System.out.println("No hay repostajes con un valor válido en 'fuel'.");
        }
        System.out.println("Refuels filtrados por fuel1");
        for (Refuel refuel : filteredRefuelsByFuel1) {

            if (refuel.getRefuelConsumption() > maxRefuelConsumption1) {
                maxRefuelConsumption1 = refuel.getRefuelConsumption();
            }
            System.out.println("Fuel1: " + refuel.getFuel() + ", CreationDate: " + refuel.getCreationDate());
        }
        updateGraph(filteredRefuelsByFuel1);

        filteredRefuelsByFuel2 = originalRefuels.stream()
                .filter(refuel -> refuel.getSecondFuel() != null)
                .collect(Collectors.toList());
        if (!filteredRefuelsByFuel2.isEmpty()) {
            for (Refuel refuel : filteredRefuelsByFuel2) {
                if (refuel.getSecondRefuelConsumption() > maxRefuelConsumption2) {
                    maxRefuelConsumption2 = refuel.getSecondRefuelConsumption();
                }
                System.out.println("Fuel1: " + refuel.getFuel() + ", CreationDate: " + refuel.getCreationDate());
            }

            updateGraph2(filteredRefuelsByFuel2);
            System.out.println("Matrícula del primer Refuel recibido: " + filteredRefuelsByFuel1.get(0).getNameVehicle());
        } else {
            System.out.println("No hay repostajes con un valor válido en 'fuel2'.");
            tvDetalleDE2.setVisibility(View.GONE);
            combinedChart2.setVisibility(View.GONE);
            tvRealConsum2.setVisibility(View.GONE);
            tvBestConsum2.setVisibility(View.GONE);
            tvWorstConsum2.setVisibility(View.GONE);
            tvBestStation2.setVisibility(View.GONE);
            tvTextRealConsum2.setVisibility(View.GONE);
            tvTextBestConsum2.setVisibility(View.GONE);
            tvTextWorstConsum2.setVisibility(View.GONE);
            tvTextBestStation2.setVisibility(View.GONE);





        }

    }


    private void filterGraphData(String filter) {
        if (filteredRefuelsByFuel1 == null || filteredRefuelsByFuel1.isEmpty()) {
            return;
        }

        List<Refuel> filteredRefuels1 = new ArrayList<>();
        List<Refuel> filteredRefuels2 = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();

        switch (filter) {
            case "ÚLTIMOS 12 MESES":
                calendar.add(Calendar.YEAR, -1);
                long lastYearTime = calendar.getTimeInMillis();
                for (Refuel refuel : filteredRefuelsByFuel1) {
                    if (isWithinRange(refuel.getCreationDate(), lastYearTime, currentTime)) {
                        filteredRefuels1.add(refuel);
                    }
                }
                for (Refuel refuel : filteredRefuelsByFuel2) {
                    if (isWithinRange(refuel.getCreationDate(), lastYearTime, currentTime)) {
                        filteredRefuels2.add(refuel);
                    }
                }
                break;

            case "ÚLTIMOS 6 MESES":
                calendar.add(Calendar.MONTH, -6);
                long lastSixMonthsTime = calendar.getTimeInMillis();
                for (Refuel refuel : filteredRefuelsByFuel1) {
                    if (isWithinRange(refuel.getCreationDate(), lastSixMonthsTime, currentTime)) {
                        filteredRefuels1.add(refuel);
                    }
                }
                for (Refuel refuel : filteredRefuelsByFuel2) {
                    if (isWithinRange(refuel.getCreationDate(), lastSixMonthsTime, currentTime)) {
                        filteredRefuels2.add(refuel);
                    }
                }
                break;

            default: // "TODOS"
                filteredRefuels1 = filteredRefuelsByFuel1;
                filteredRefuels2 = filteredRefuelsByFuel2;
                break;
        }
        System.out.println("Refuels filtrados por fuel1 y por fecha");
        for (Refuel refuel : filteredRefuels1) {
            System.out.println("Fuel1: " + refuel.getFuel() + ", CreationDate: " + refuel.getCreationDate());
        }

        System.out.println("Refuels filtrados por fuel2 y por fecha");
        for (Refuel refuel : filteredRefuels2) {
            System.out.println("Fuel2: " + refuel.getSecondFuel() + ", CreationDate: " + refuel.getCreationDate());
        }
        // Actualiza la gráfica con los datos filtrados
        updateGraph(filteredRefuels1);
        if (!filteredRefuels2.isEmpty()){
            updateGraph2(filteredRefuels2);
        }

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

        float realConsumTotal = 0;
        int realEntries = 0;

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
            System.out.println("Iteración " + i + ": Fuel1 = " + refuel.getFuel() + ": refuelConsumption = " + refuel.getRefuelConsumption() + ", CreationDate = " + refuel.getCreationDate());
            // Datos para las barras
            float consumption = refuel.getRefuelConsumption();
            barEntries.add(new BarEntry(i, consumption));

            // Primera línea: consumo real
            if (refuel.isFulled() && i > 0 && refuels.get(i - 1).isFulled()) {
                realConsumTotal += refuel.getRefuelConsumption();
                realEntries ++;
                lineEntries1.add(new Entry(i, realConsumTotal/realEntries));
            }
            realConsum = realConsumTotal/realEntries;

            // Segunda línea: promedio de consumo

            float medConsumption = refuel.getMedConsumption();
            lineEntries2.add(new Entry(i, medConsumption));

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
        leftAxis.setAxisMaximum(maxRefuelConsumption1+1);
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

    private void updateGraph2(List<Refuel> refuels) {
        List<BarEntry> barEntries2 = new ArrayList<>();
        List<Entry> lineEntries3 = new ArrayList<>();
        List<Entry> lineEntries4 = new ArrayList<>();

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
            System.out.println("Iteración " + i + ": Fuel2 = " + refuel.getSecondFuel() + ": SecondrefuelConsumption = " + refuel.getSecondRefuelConsumption() + ", CreationDate = " + refuel.getCreationDate());

            // Datos para las barras
            float consumption = refuel.getSecondRefuelConsumption();
            barEntries2.add(new BarEntry(i, consumption));

            // Primera línea: consumo real
            if (refuel.isSecondFulled() && i > 0 && refuels.get(i - 1).isSecondFulled()) {
                realConsumTotal2 += refuel.getSecondRefuelConsumption();
                realEntries2 ++;
                lineEntries3.add(new Entry(i, realConsumTotal2/realEntries2));
            }
            realConsum2 = realConsumTotal2/realEntries2;

            // Segunda línea: promedio de consumo

            float medConsumption = refuel.getSecondMedConsumption();
            lineEntries4.add(new Entry(i, medConsumption));

        }


        showData2(refuels);

        BarDataSet barDataSet = new BarDataSet(barEntries2, "Consumo del repostaje");
        barDataSet.setColor(Color.parseColor("#156082"));
        barDataSet.setValueTextSize(16f);

        LineDataSet lineDataSet1 = new LineDataSet(lineEntries3, "Consumo real");
        lineDataSet1.setColor(Color.GREEN);
        lineDataSet1.setCircleColor(Color.GREEN);
        lineDataSet1.setLineWidth(4f);
        lineDataSet1.setDrawValues(false);

        LineDataSet lineDataSet2 = new LineDataSet(lineEntries4, "Consumo Promedio");
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setCircleColor(Color.RED);
        lineDataSet2.setLineWidth(4f);
        lineDataSet2.setDrawValues(false);

        // Configuración del eje X para mostrar fechas
        XAxis xAxis = combinedChart2.getXAxis();
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
        xAxis.setAxisMaximum(barEntries2.size() - 0.5f); // Un poco después de la última barra

        YAxis leftAxis = combinedChart2.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(maxRefuelConsumption2+1);
        leftAxis.setGranularity(1f);
        combinedChart2.getAxisRight().setEnabled(false);
        combinedChart2.getDescription().setEnabled(false);
        combinedChart2.setDragEnabled(true);
        combinedChart2.setScaleEnabled(true);


        // Crear los objetos BarData y LineData
        BarData barData = new BarData(barDataSet);
        LineData lineData = new LineData(lineDataSet1, lineDataSet2);

        // Crear el gráfico combinado y añadir los conjuntos de datos
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);  // Añadir barras
        combinedData.setData(lineData); // Añadir las dos líneas

        combinedChart2.setData(combinedData);
        combinedChart2.invalidate();
    }


    public void showData(List<Refuel> refuels){

        List<Entry> lineEntries1 = new ArrayList<>();
        tvDetalleDE.setText("Consumos con " + refuels.get(0).getFuel());
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

        Refuel bestRefuel = null;
        Refuel worstRefuel = null;

        for (int i = 1; i < refuels.size(); i++) {
            Refuel current = refuels.get(i);

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

        tvRealConsum.setText(String.format("%.2f", realConsum));



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

    public void showData2(List<Refuel> refuels){

        List<Entry> lineEntries2 = new ArrayList<>();
        tvDetalleDE2.setText("Consumos con " + refuels.get(0).getSecondFuel());
        tvTextRealConsum2.setText("Consumo real del vehículo:");
        tvTextBestConsum2.setText("Fecha del repostaje con mejor consumo:");
        tvTextWorstConsum2.setText("Fecha del repostaje con peor consumo:");
        tvTextBestStation2.setText("Estación con el mejor consumo:");

        if (refuels == null || refuels.isEmpty()) {
            tvRealConsum2.setText("Sin datos disponibles");
            tvBestConsum2.setText("Sin datos disponibles");
            tvWorstConsum2.setText("Sin datos disponibles");
            tvBestStation2.setText("Sin datos disponibles");
            return;
        }

        Refuel bestRefuel = null;
        Refuel worstRefuel = null;

        for (int i = 1; i < refuels.size(); i++) {
            Refuel current = refuels.get(i);

            if (current == null || current.getSecondRefuelConsumption() == 0 || i - 1 < 0) continue;

            if (bestRefuel == null || current.getSecondRefuelConsumption() < bestRefuel.getSecondRefuelConsumption()) {
                bestRefuel = current;
            }

            if (worstRefuel == null || current.getSecondRefuelConsumption() > worstRefuel.getSecondRefuelConsumption()) {
                worstRefuel = current;
            }
        }

        // Mostrar el mejor repostaje
        if (bestRefuel != null) {
            Refuel previousRefuel = refuels.get(refuels.indexOf(bestRefuel) - 1);
            String bestText = String.format("%.2f", bestRefuel.getSecondRefuelConsumption()) + " / " + previousRefuel.getCreationDate();
            tvBestConsum2.setText(bestText);
        } else {
            tvBestConsum2.setText("No hay datos para el mejor repostaje");
        }

        // Mostrar el peor repostaje
        if (worstRefuel != null) {
            Refuel previousRefuel = refuels.get(refuels.indexOf(worstRefuel) - 1);
            String worstText = String.format("%.2f", worstRefuel.getSecondRefuelConsumption()) + " / " + previousRefuel.getCreationDate();
            tvWorstConsum2.setText(worstText);
        } else {
            tvWorstConsum2.setText("No hay datos para el peor repostaje");
        }

        // Mostrar el consumo real promedio (opcional)

        tvRealConsum2.setText(String.format("%.2f", realConsum2));



        if (refuels == null || refuels.size() < 2) {
            tvBestStation2.setText("Mejor estación: No disponible");
        } else {
            Refuel bestRefuel2 = null;
            int bestRefuelIndex = -1;
            float bestConsumption = Float.MAX_VALUE;

            // Buscar el repostaje con el mejor consumo
            for (int i = 1; i < refuels.size(); i++) {
                Refuel currentRefuel = refuels.get(i);
                if (currentRefuel.isSecondFulled() && currentRefuel.getSecondRefuelConsumption() < bestConsumption) {
                    bestConsumption = currentRefuel.getSecondRefuelConsumption();
                    bestRefuel2 = currentRefuel;
                    bestRefuelIndex = i;
                }
            }

            // Buscar el nombre de la estación del repostaje anterior
            if (bestRefuel2 != null && bestRefuelIndex > 0) {
                Refuel previousRefuel = refuels.get(bestRefuelIndex - 1);
                tvBestStation2.setText(previousRefuel.getNameStation());
            } else {
                tvBestStation2.setText("Mejor estación: No disponible");
            }
        }
    }

    @Override
    public void showMessage(String message) {

    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}

