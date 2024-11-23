package com.sphy.pfc_app.view.vehicles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.SpinnerAdapter;
import com.sphy.pfc_app.contract.vehicles.VehicleRegisterContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.presenter.vehicles.VehicleDetailsPresenter;
import com.sphy.pfc_app.presenter.vehicles.VehicleRegisterPresenter;
import com.sphy.pfc_app.view.BaseActivity;

public class RegisterVehicleView extends BaseActivity implements VehicleRegisterContract.View{

    private ImageButton menuButton;
    private Button create_button;
    private VehicleRegisterPresenter presenter;
    private Spinner fuelTypeSpinner;

    private TextView username;
    private Button backButton;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        menuButton = findViewById(R.id.menuButton);
        create_button = findViewById(R.id.create_button);
        setupMenuButton(menuButton);
        username = findViewById(R.id.userNameTextView);

        presenter = new VehicleRegisterPresenter(this);

        Spinner fuel1 = findViewById(R.id.fuelTypeSpinner1);
        Spinner fuel2 = findViewById(R.id.fuelTypeSpinner2);
        SpinnerAdapter.populateFuelsSpinner(this, fuel1);
        SpinnerAdapter.populateFuelsSpinner(this, fuel2);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user);

        CheckBox checkFuel2 = findViewById(R.id.vehicle_fuel2check);
        TextView sFuelText = findViewById(R.id.textView9);

        sFuelText.setEnabled(false);
        fuel2.setEnabled(false);

        checkFuel2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sFuelText.setEnabled(isChecked);
            fuel2.setEnabled(isChecked);
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMain(v);
            }
        });
    }


    public void createVehicle(View view) {


        EditText etLicense = findViewById(R.id.license_plate);
        EditText etBrand = findViewById(R.id.vehicle_brand);
        EditText etModel = findViewById(R.id.vehicle_model);
        Spinner fuel1 = findViewById(R.id.fuelTypeSpinner1);
        CheckBox checkFuel2 = findViewById(R.id.vehicle_fuel2check);
        Spinner fuel2 = findViewById(R.id.fuelTypeSpinner2);
        EditText etKms = findViewById(R.id.vehicle_kms);
        TextView sFuelText = findViewById(R.id.textView9);

        sFuelText.setEnabled(false);
        fuel2.setEnabled(false);

        checkFuel2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sFuelText.setEnabled(isChecked);
            fuel2.setEnabled(isChecked);
        });


        String license = etLicense.getText().toString();
        String brand = etBrand.getText().toString();
        String model = etModel.getText().toString();
        String pFuel = fuel1.getSelectedItem().toString();
        String sFuel = fuel2.getSelectedItem().toString();


        int kms = Integer.parseInt(etKms.getText().toString());

        Vehicle vehicle = new Vehicle(0, license, brand, model, pFuel, sFuel, kms, 0, null, false, null,0);
        presenter.insertVehicle(vehicle);
    }

    @Override
    public void showInsertSuccessMessage() {
        Toast.makeText(this,"Vehiculo añadido", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showInsertErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearFields() {

    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
