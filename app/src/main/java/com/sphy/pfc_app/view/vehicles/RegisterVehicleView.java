package com.sphy.pfc_app.view.vehicles;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.vehicles.VehicleRegisterContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.presenter.vehicles.VehicleDetailsPresenter;
import com.sphy.pfc_app.presenter.vehicles.VehicleRegisterPresenter;
import com.sphy.pfc_app.view.BaseActivity;

public class RegisterVehicleView extends BaseActivity implements VehicleRegisterContract.View{

    private ImageButton menuButton;
    private Button create_button;
    private VehicleRegisterPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        menuButton = findViewById(R.id.menuButton);
        create_button = findViewById(R.id.create_button);
        setupMenuButton(menuButton);

        presenter = new VehicleRegisterPresenter(this);



    }

    public void createVehicle(View view) {
        EditText etLicense = findViewById(R.id.license_plate);
        EditText etBrand = findViewById(R.id.vehicle_brand);
        EditText etModel = findViewById(R.id.vehicle_model);
        EditText etFuel1 = findViewById(R.id.vehicle_fuel1);
        CheckBox checkFuel2 = findViewById(R.id.vehicle_fuel2check);
        EditText etFuel2 = findViewById(R.id.vehicle_fuel2);
        EditText etKms = findViewById(R.id.vehicle_kms);


        String license = etLicense.getText().toString();
        String brand = etBrand.getText().toString();
        String model = etModel.getText().toString();
        String fuel1 = etFuel1.getText().toString();

        String fuel2 = etFuel2.getText().toString();
        int kms = Integer.parseInt(etKms.getText().toString());

        Vehicle vehicle = new Vehicle(0, license, brand, model, fuel1, fuel2, kms, 0, null, false, null);
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
}
