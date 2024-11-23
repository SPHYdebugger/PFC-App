package com.sphy.pfc_app.view.vehicles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.presenter.vehicles.VehicleDetailsPresenter;
import com.sphy.pfc_app.view.BaseActivity;
import com.sphy.pfc_app.view.refuels.RefuelListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;


public class VehicleConsumDetailsView extends BaseActivity implements VehicleDetailsContract.View {

    private TextView tvVehicleId;



    private TextView tvDetalleDE;






    private VehicleDetailsContract.Presenter presenter;
    private long vehicleId;

    private String licensePlateGet;

    private ImageButton menuButton;

    private Vehicle temporalVehicle;
    private int vehicleRefuels;
    private long refuelsLong;

    private TextView username;
    private Button backButton;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consum_vehicle);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        tvDetalleDE = findViewById(R.id.detalleDE);
        username = findViewById(R.id.userNameTextView);
        backButton = findViewById(R.id.backButton);
        BarChart barChart = findViewById(R.id.barCha);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1f, 10f));
        entries.add(new BarEntry(2f, 20f));
        entries.add(new BarEntry(3f, 15f));

        BarDataSet dataSet = new BarDataSet(entries, "Consumo de Combustible");
        dataSet.setColor(Color.BLUE);
        BarData barData = new BarData(dataSet);

        barChart.setData(barData);
        barChart.invalidate();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMain(v);
            }
        });

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
        tvDetalleDE.setText(text);



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

    public void backMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }



}