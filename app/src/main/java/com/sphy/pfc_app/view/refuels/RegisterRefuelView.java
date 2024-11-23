package com.sphy.pfc_app.view.refuels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.SpinnerAdapter;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.presenter.Refuels.RefuelRegisterPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RegisterRefuelView extends BaseActivity {

    private ImageButton menuButton;
    private Button create_button;
    private RefuelRegisterPresenter presenter;


    private EditText eurosRepostadosEditText;
    private EditText fuelPriceEditText;
    private EditText vehicleKmEditText;
    private Spinner fuelTypeSpinner;
    private Spinner stationSpinner;

    private TextView selectedVehicleTextView;
    private CheckBox full;

    private TextView username;

    private SharedPreferencesManager sharedPreferencesManager;

    private long vehicleId;
    private long stationId;
    private String license;
    private String kms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_refuel);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        vehicleId = getIntent().getLongExtra("vehicleId", -1);
        license = getIntent().getStringExtra("vehicleLicense");
        kms = getIntent().getStringExtra("vehicleKms");

        username = findViewById(R.id.userNameTextView);


        if (vehicleId == -1) {
            Toast.makeText(this, "Error: Vehículo no encontrado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }



        menuButton = findViewById(R.id.menuButton);
        create_button = findViewById(R.id.create_button);
        eurosRepostadosEditText = findViewById(R.id.eurosRepostadosEditText);
        fuelPriceEditText = findViewById(R.id.fuelPriceEditText);
        vehicleKmEditText = findViewById(R.id.vehicleKmEditText);
        fuelTypeSpinner = findViewById(R.id.fuelTypeSpinner);
        stationSpinner = findViewById(R.id.stationSpinner);
        full = findViewById(R.id.checkBox2);
        selectedVehicleTextView = findViewById(R.id.selectedVehicleTextView);
        selectedVehicleTextView.setText(license);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user.toUpperCase());

        vehicleKmEditText.setText(String.valueOf(kms));
        vehicleKmEditText.setTextColor(Color.GRAY);
        vehicleKmEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    vehicleKmEditText.setTextColor(Color.BLACK);
                } else {
                    if (vehicleKmEditText.getText().toString().isEmpty()) {
                        vehicleKmEditText.setTextColor(Color.GRAY);
                    }
                }
            }
        });

        vehicleKmEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String enteredKmText = vehicleKmEditText.getText().toString();

                if (!enteredKmText.isEmpty()) {
                    int enteredKm = Integer.parseInt(enteredKmText);
                    if (enteredKm <= Integer.parseInt(kms)) {
                        vehicleKmEditText.setError("El valor no puede ser menor o igual al actual.");
                        return false;
                    }

                    if (enteredKm > Integer.parseInt(kms) + 1200) {
                        confirmRegisterKms();
                        return false;
                    }
                }

                return true;
            }
        });


        SpinnerAdapter.populateFuelTypeSpinner(this, fuelTypeSpinner, vehicleId);
        SpinnerAdapter.populateStationSpinner(this,stationSpinner);


        //Recoger el objeto de la station
        stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                List<StationDTO> stations = (List<StationDTO>) stationSpinner.getTag();

                StationDTO selectedStation = stations.get(position);

                stationId = selectedStation.getId();
                String stationName = selectedStation.getName();
                String stationAddress = selectedStation.getAddress();

                Toast.makeText(getApplicationContext(), "Estacion seleccionada con id " + stationId, Toast.LENGTH_SHORT).show();
                Log.d("Station Selected", "Station ID: " + stationId + ", Name: " + stationName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(getApplicationContext(), "¡Por favor, selecciona una estación de servicio!", Toast.LENGTH_SHORT).show();
            }
        });
        setupMenuButton(menuButton);
        presenter = new RefuelRegisterPresenter(this);
        create_button.setOnClickListener(v -> confirmRegisterRefuel());
    }

    public void registerRefuel(View view) {

        try {

            float amount = Float.parseFloat(eurosRepostadosEditText.getText().toString());
            float fuelPrice = Float.parseFloat(fuelPriceEditText.getText().toString());
            int vehicleKm = Integer.parseInt(vehicleKmEditText.getText().toString());
            String fuelType = fuelTypeSpinner.getSelectedItem().toString();


            Refuel refuel = new Refuel();
            refuel.setFuel(fuelType);
            refuel.setAmount(amount);
            refuel.setPrice(fuelPrice);
            refuel.setKmTotal(vehicleKm);
            refuel.setFulled(full.isChecked());

            presenter.insertRefuel(vehicleId, stationId, refuel);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, complete todos los campos correctamente.", Toast.LENGTH_LONG).show();
        }
    }

    private void confirmRegisterRefuel() {
        String llenado = full.isChecked() ? "SI" : "NO";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERTA, CONFIRMACIÓN");
        String message = "<b>¿SON ESTOS DATOS CORRECTOS?</b><br>" +
                "<b>Vehículo:</b> " + license + "<br>" +
                "<b>Combustible:</b> " + fuelTypeSpinner.getSelectedItem().toString() + "<br>" +
                "<b>Euros repostados:</b> " + eurosRepostadosEditText.getText().toString() + "<br>" +
                "<b>Precio del combustible:</b> " + fuelPriceEditText.getText().toString() + "<br>" +
                "<b>Kilómetros actuales del vehículo:</b> " + vehicleKmEditText.getText().toString() + "<br>" +
                "<b>Llenado de depósito:</b> " + llenado;

        builder.setMessage(Html.fromHtml(message));



        builder.setPositiveButton("Sí", (dialog, which) -> {
            registerRefuel(create_button);
            showRefuelSuccessMessage();
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        // Mostrar el aviso
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void confirmRegisterKms() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERTA, CONFIRMACIÓN DE KILÓMETROS");
        String message = "<b>Los kilómetros actuales del vehículo, superan los 1200Kms desde el anterior repostaje. </b>" + "<br>" +
                "<p>Si sigue adelante, las mediciones de este repostaje no se tendrán en cuenta a la hora de calcular las medias de consumos.";

        builder.setMessage(Html.fromHtml(message));





        builder.setNegativeButton("OK", (dialog, which) -> dialog.dismiss());

        // Mostrar el aviso
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showRefuelSuccessMessage() {
        Toast.makeText(this, "Repostaje registrado exitosamente", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RegisterRefuelView.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public void showRefuelErrorMessage() {
        Toast.makeText(this, "Error al registrar el repostaje", Toast.LENGTH_LONG).show();
    }


}

