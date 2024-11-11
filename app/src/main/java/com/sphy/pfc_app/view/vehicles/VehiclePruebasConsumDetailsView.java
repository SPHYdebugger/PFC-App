package com.sphy.pfc_app.view.vehicles;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
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
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.refuels.RefuelDetailsContract;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.view.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VehiclePruebasConsumDetailsView extends BaseActivity implements VehicleDetailsContract.View {

    private TextView tvVehicleId;
    private VehicleDetailsContract.Presenter presenter;
    private long vehicleId;
    private String licensePlateGet;
    private ImageButton menuButton;
    private Vehicle temporalVehicle;
    private int vehicleRefuels;
    private long refuelsLong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consum_vehicle_pruebas);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);



        // Inicializar la gráfica
        BarChart barChart = findViewById(R.id.barCha);

        // Crear datos de ejemplo con fechas y consumos
        List<BarEntry> entries = new ArrayList<>();

        // Datos de ejemplo: consumos de combustible para diferentes días
        long date1 = System.currentTimeMillis(); // Fecha actual
        long date2 = date1 + 86400000L; // Fecha 1 día después
        long date3 = date2 + 86400000L; // Fecha 2 días después

        // Aquí en lugar de usar fechas en milisegundos, usamos índices (0, 1, 2)
        entries.add(new BarEntry(0, 10f)); // Día 1
        entries.add(new BarEntry(1, 20f)); // Día 2
        entries.add(new BarEntry(2, 15f)); // Día 3

        // Crear conjunto de datos
        BarDataSet dataSet = new BarDataSet(entries, "Consumo de Combustible");
        dataSet.setColor(Color.BLUE);
        BarData barData = new BarData(dataSet);

        // Configurar el gráfico
        barChart.setData(barData);
        barChart.invalidate(); // Refrescar la gráfica

        // Personalizar el eje X para mostrar fechas
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                // Aquí convertimos el valor del índice (0, 1, 2) a una fecha
                int index = (int) value;
                long[] dates = {date1, date2, date3};  // Definir las fechas como array

                if (index >= 0 && index < dates.length) {
                    Date date = new Date(dates[index]);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    return dateFormat.format(date); // Formatear la fecha
                } else {
                    return "";  // Retornar vacío si el índice es inválido
                }
            }
        });

        // Configuración adicional del eje X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Asegura que solo se muestren las fechas correctas
        xAxis.setLabelRotationAngle(45f); // Rotar etiquetas para mejor legibilidad

        // Configuración del eje Y
        barChart.getAxisLeft().setAxisMinimum(0f); // Establecer límite inferior
        barChart.getAxisLeft().setGranularity(1f); // Granularidad en el eje Y

        // Configuración de otras opciones del gráfico
        barChart.getDescription().setEnabled(false); // Desactivar la descripción del gráfico
        barChart.setTouchEnabled(true); // Habilitar interacción
        barChart.setDragEnabled(true); // Habilitar arrastre
        barChart.setScaleEnabled(true); // Habilitar zoom
        barChart.getAxisRight().setEnabled(false); // Desactivar el eje Y derecho
    }



    @Override
    protected void onResume() {
        super.onResume();
        /*Intent intent = getIntent();
        vehicleId = intent.getLongExtra("vehicleId",vehicleId);
        presenter.getVehicle(vehicleId);*/



    }

    @Override
    public void displayVehicleDetails(VehicleDTO vehicle) {

        String text = "DETALLE DEL VEHÍCULO: " + vehicle.getLicensePlate();




    }

    @Override
    public void showUpdateSuccessMessage() {
        Toast.makeText(this, "Vehiculo modificado", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUpdateErrorMessage() {
        Toast.makeText(this, "Error al actualizar el vehiculo", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteSuccessMessage() {
        Toast.makeText(this, "vehículo eliminado correctamente", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showDeleteErrorMessage() {
        Toast.makeText(this, "Error al eliminar el vehículo", Toast.LENGTH_LONG).show();
    }





}