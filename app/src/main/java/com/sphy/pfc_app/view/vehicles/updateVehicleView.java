package com.sphy.pfc_app.view.vehicles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.vehicles.UpdateVehicleContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.model.vehicles.UpdateVehicleModel;
import com.sphy.pfc_app.presenter.vehicles.UpdateVehiclePresenter;
import com.sphy.pfc_app.presenter.vehicles.VehicleDetailsPresenter;
import com.sphy.pfc_app.view.BaseActivity;

public class updateVehicleView extends BaseActivity implements UpdateVehicleContract.View {

    private TextView tvLicense;
    private EditText tvBrand;
    private EditText tvModel;
    private EditText tvKMs;
    private TextView tvDate;
    private SharedPreferencesManager sharedPreferencesManager;
    private UpdateVehicleContract.Presenter presenter;
    private long vehicleId;
    private Button updateVehicle;
    private ImageButton menuButton;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        tvLicense = findViewById(R.id.detail_license);
        tvBrand = findViewById(R.id.detail_brand);
        tvModel = findViewById(R.id.detail_model);
        tvKMs = findViewById(R.id.detail_kms);
        tvDate = findViewById(R.id.date);
        updateVehicle = findViewById(R.id.updateVehicle);
        menuButton = findViewById(R.id.menuButton);
        username = findViewById(R.id.userNameTextView);
        setupMenuButton(menuButton);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user.toUpperCase());
        presenter = new UpdateVehiclePresenter(this);

        vehicleId = getIntent().getLongExtra("id", -1);
        presenter.fetchVehicleDetails(vehicleId);


        updateVehicle.setOnClickListener(v -> {
            Vehicle updatedVehicle = new Vehicle();

            updatedVehicle.setLicensePlate(tvLicense.getText().toString());
            updatedVehicle.setBrand(tvBrand.getText().toString());
            updatedVehicle.setModel(tvModel.getText().toString());
            updatedVehicle.setKmActual(Integer.parseInt(tvKMs.getText().toString()));



            presenter.updateVehicle(updatedVehicle);
        });
    }

    @Override
    public void displayVehicleDetails(Vehicle vehicle) {
        tvLicense.setText(vehicle.getLicensePlate());
        tvBrand.setText(vehicle.getBrand());
        tvModel.setText(vehicle.getModel());
        tvKMs.setText(String.valueOf(vehicle.getKmActual()));
        tvDate.setText(vehicle.getRegistrationDate());

    }

    @Override
    public void showUpdateSuccessMessage() {
        Toast.makeText(this, "Vehículo actualizado correctamente", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showUpdateErrorMessage() {
        Toast.makeText(this, "Vehículo actualizado correctamente", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }


}
