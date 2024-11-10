package com.sphy.pfc_app.view.vehicles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.presenter.vehicles.VehicleDetailsPresenter;
import com.sphy.pfc_app.view.BaseActivity;
import com.sphy.pfc_app.view.refuels.RefuelListView;


public class VehicleDetailsView extends BaseActivity implements VehicleDetailsContract.View {

    private TextView tvVehicleId;
    private TextView tvLicense;
    private TextView tvBrand;
    private TextView tvModel;

    private TextView tvKMs;

    private TextView tvRefuels;
    private TextView tvfuel;
    private TextView tvConsum;
    private TextView tvDate;

    private TextView tvDetalleDE;

    private Button goRefuels;




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
        setContentView(R.layout.activity_details_vehicle);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        tvDetalleDE = findViewById(R.id.detalleDE);

        tvLicense = findViewById(R.id.detail_license);
        tvBrand = findViewById(R.id.detail_brand);
        tvModel = findViewById(R.id.detail_model);
        tvKMs = findViewById(R.id.detail_kms);
        tvRefuels = findViewById(R.id.detail_refuels);
        goRefuels = findViewById(R.id.goRefuels);
        tvfuel = findViewById(R.id.fuel);
        tvConsum = findViewById(R.id.consum);
        tvDate = findViewById(R.id.date);


        presenter = new VehicleDetailsPresenter(this);

        Intent intent = getIntent();
        vehicleId = intent.getLongExtra("id", vehicleId);
        licensePlateGet = intent.getStringExtra("licensePlate");
        System.out.println("valor que se recoge " + licensePlateGet);
        presenter.getVehicle(vehicleId);


        goRefuels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goVehicleRefuels();
            }
        });
    }

    private void goVehicleRefuels() {
        Intent intent = new Intent(VehicleDetailsView.this, RefuelListView.class);
        System.out.println("valor que se pasa " + licensePlateGet);
        intent.putExtra("identifier", licensePlateGet);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        vehicleId = intent.getLongExtra("vehicleId",vehicleId);
        presenter.getVehicle(vehicleId);
    }

    @Override
    public void displayVehicleDetails(VehicleDTO vehicle) {

        String text = "DETALLE DEL VEHÍCULO: " + vehicle.getLicensePlate();
        tvDetalleDE.setText(text);

        tvLicense.setText(vehicle.getLicensePlate());
        tvBrand.setText(vehicle.getBrand());
        tvModel.setText(vehicle.getModel());
        tvKMs.setText(String.valueOf(vehicle.getKmActual()));
        tvRefuels.setText(String.valueOf(vehicle.getRefuels()));
        tvfuel.setText(vehicle.getFuel1());
        tvConsum.setText(String.valueOf(vehicle.getMedConsumption()));
        tvDate.setText(vehicle.getRegistrationDate());

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



    private void confirmDeleteVehicle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERTA, CONFIRMACIÓN");
        builder.setMessage("¿EStá seguro de querer borrar el vehículo?");

        // Botón confirmar
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteVehicle(vehicleId);
            }
        });

        // Botón cancelar
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Mostrar el aviso
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}