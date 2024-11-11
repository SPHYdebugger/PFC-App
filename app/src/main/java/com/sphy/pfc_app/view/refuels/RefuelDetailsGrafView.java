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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
        barChart = findViewById(R.id.barCha);

        presenter = new RefuelListPresenter(this);
        presenter.findRefuelByIdentifier("1111AAA");

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void listRefuels(List<Refuel> refuels) {
        List<BarEntry> entries = new ArrayList<>();

        // Iterar sobre los repostajes y agregar los datos en el gráfico
        for (int i = 0; i < refuels.size(); i++) {
            Refuel refuel = refuels.get(i);
            float consumption = refuel.getRefuelConsumption();  // Consumo de combustible
            entries.add(new BarEntry(i, consumption));  // 'i' es el índice que se usa para el eje X
        }

        // Crear el conjunto de datos y configurarlo
        BarDataSet dataSet = new BarDataSet(entries, "Consumo de Combustible");
        dataSet.setColor(Color.BLUE);
        BarData barData = new BarData(dataSet);

        // Habilitar los valores en las barras
        dataSet.setDrawValues(true);  // Habilitar mostrar los valores sobre las barras
        dataSet.setValueTextColor(Color.BLACK);  // Color del texto
        dataSet.setValueTextSize(10f);  // Tamaño del texto

        // Establecer el ValueFormatter para los valores
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Convertir el valor a un String con 2 decimales
                return String.format("%.2f", value);  // Formatear el valor con 2 decimales
            }
        });

        // Configurar el gráfico
        barChart.setData(barData);
        barChart.invalidate();  // Refrescar la gráfica

        // Configuración del eje X para mostrar las fechas
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int index = (int) value;  // Convertir el valor flotante a entero
                if (index >= 0 && index < refuels.size()) {
                    String creationDateStr = refuels.get(index).getCreationDate();  // Obtener la fecha de creación como String
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Usar el formato adecuado
                    try {
                        Date creationDate = dateFormat.parse(creationDateStr);  // Parsear la fecha a Date
                        SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy");  // Formatear para mostrar
                        return formattedDate.format(creationDate);  // Formatear la fecha para mostrarla
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return "";
            }
        });

        // Configuración del eje X
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setGranularity(1f);  // Granularidad en el eje X
        xAxis.setLabelRotationAngle(90f);  // Rotar las etiquetas del eje X a 90 grados
        xAxis.setTextSize(14f);

        // Configuración del eje Y
        barChart.getAxisLeft().setAxisMinimum(0f);  // Establecer límite inferior
        barChart.getAxisLeft().setGranularity(1f);  // Granularidad en el eje Y

        // Otras configuraciones del gráfico
        barChart.getDescription().setEnabled(false);  // Desactivar la descripción del gráfico

        barChart.setDragEnabled(true);  // Habilitar arrastre
        barChart.setScaleEnabled(true);  // Habilitar zoom
        barChart.getAxisRight().setEnabled(false);  // Desactivar el eje Y derecho
    }


    @Override
    public void showMessage(String message) {

    }
}
