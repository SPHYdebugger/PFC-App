package com.sphy.pfc_app.view.refuels;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.SpinnerAdapter;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.model.vehicles.VehicleDetailsModel;
import com.sphy.pfc_app.presenter.Refuels.RefuelRegisterPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.util.List;

public class RegisterRefuelView extends BaseActivity {

    private ImageButton menuButton;
    private Button create_button;
    private RefuelRegisterPresenter presenter;


    private EditText eurosRepostadosEditText;
    private EditText fuelPriceEditText;
    private EditText vehicleKmFuel1EditText;
    private Spinner fuelTypeSpinner;
    private int selectedPosition;
    private int totalPositions;
    private Spinner stationSpinner;

    private TextView selectedVehicleTextView;
    private CheckBox full;

    private TextView username;

    private SharedPreferencesManager sharedPreferencesManager;


    private long vehicleId;
    private long stationId;
    private String stationName;
    private String license;
    private String kms;

    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;
    private TextView textView11;


    private EditText eurosRepostadosEditText2;
    private EditText fuelPriceEditText2;
    private EditText vehicleKmEditText2;
    private Spinner fuelTypeSpinner2;
    private CheckBox full2;
    private CheckBox doubleRefuel;
    private LinearLayout linearLayout;

    private long userId;



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
        vehicleKmFuel1EditText = findViewById(R.id.vehicleKmEditText);
        fuelTypeSpinner = findViewById(R.id.fuelTypeSpinner);
        stationSpinner = findViewById(R.id.stationSpinner);
        full = findViewById(R.id.checkBox2);
        selectedVehicleTextView = findViewById(R.id.selectedVehicleTextView);
        selectedVehicleTextView.setText(license);

        linearLayout = findViewById(R.id.repostajeDetailsLayout);
        eurosRepostadosEditText2 = findViewById(R.id.eurosRepostadosEditText2);
        fuelPriceEditText2 = findViewById(R.id.fuelPriceEditText2);
        vehicleKmEditText2 = findViewById(R.id.vehicleKmEditText2);
        fuelTypeSpinner2 = findViewById(R.id.fuelTypeSpinner2);
        doubleRefuel = findViewById(R.id.checkBox3);
        full2 = findViewById(R.id.checkBox4);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        setupMenuButton(menuButton);
        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user.toUpperCase());
        userId = sharedPreferencesManager.getUserIdFromJWT(token);
        System.out.println("el userId es...." + userId);

        eurosRepostadosEditText2.setEnabled(false);
        fuelPriceEditText2.setEnabled(false);
        vehicleKmEditText2.setEnabled(false);
        fuelTypeSpinner2.setEnabled(false);
        full2.setEnabled(false);


        SpinnerAdapter.populateFuelTypeSpinner(this, fuelTypeSpinner, vehicleId);
        SpinnerAdapter.populateFuelType2Spinner(this, fuelTypeSpinner2, vehicleId);
        SpinnerAdapter.populateStationSpinner(this,stationSpinner);

        fuelTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                System.out.println("posicion del spinner..." + selectedPosition);
                totalPositions = parent.getCount();
                System.out.println("posiciones totales..." + totalPositions);


                if (selectedPosition == 0 && totalPositions == 1) {
                    // Oculta los campos del segundo repostaje
                    doubleRefuel.setVisibility(View.INVISIBLE);
                    textView7.setVisibility(View.INVISIBLE);
                    textView8.setVisibility(View.INVISIBLE);
                    textView9.setVisibility(View.INVISIBLE);
                    textView10.setVisibility(View.INVISIBLE);
                    textView11.setVisibility(View.INVISIBLE);
                    eurosRepostadosEditText2.setVisibility(View.INVISIBLE);
                    fuelPriceEditText2.setVisibility(View.INVISIBLE);
                    vehicleKmEditText2.setVisibility(View.INVISIBLE);
                    fuelTypeSpinner2.setVisibility(View.INVISIBLE);
                    full2.setVisibility(View.INVISIBLE);

                    // Limpia los valores de los campos del segundo repostaje
                    eurosRepostadosEditText2.setText("");
                    fuelPriceEditText2.setText("");
                    vehicleKmEditText2.setText("");
                    fuelTypeSpinner2.setSelection(0);
                    full2.setChecked(false);

                    // Ajusta el tamaño del layout si es necesario
                    LinearLayout linearLayout = findViewById(R.id.repostajeDetailsLayout);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams.height = 1500;
                    linearLayout.setLayoutParams(layoutParams);

                } else if (selectedPosition == 1) { // Si está en la posición 1, ocultar los campos del segundo repostaje
                    doubleRefuel.setVisibility(View.INVISIBLE);
                    textView7.setVisibility(View.INVISIBLE);
                    textView8.setVisibility(View.INVISIBLE);
                    textView9.setVisibility(View.INVISIBLE);
                    textView10.setVisibility(View.INVISIBLE);
                    textView11.setVisibility(View.INVISIBLE);
                    eurosRepostadosEditText2.setVisibility(View.INVISIBLE);
                    fuelPriceEditText2.setVisibility(View.INVISIBLE);
                    vehicleKmEditText2.setVisibility(View.INVISIBLE);
                    fuelTypeSpinner2.setVisibility(View.INVISIBLE);
                    full2.setVisibility(View.INVISIBLE);

                    // Limpia los valores de los campos del segundo repostaje
                    eurosRepostadosEditText2.setText("");
                    fuelPriceEditText2.setText("");
                    vehicleKmEditText2.setText("");
                    fuelTypeSpinner2.setSelection(0);
                    full2.setChecked(false);


                    LinearLayout linearLayout = findViewById(R.id.repostajeDetailsLayout);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams.height = 1500;
                    linearLayout.setLayoutParams(layoutParams);

                } else {
                    doubleRefuel.setVisibility(View.VISIBLE);
                    textView7.setVisibility(View.VISIBLE);
                    textView8.setVisibility(View.VISIBLE);
                    textView9.setVisibility(View.VISIBLE);
                    textView10.setVisibility(View.VISIBLE);
                    textView11.setVisibility(View.VISIBLE);
                    eurosRepostadosEditText2.setVisibility(View.VISIBLE);
                    fuelPriceEditText2.setVisibility(View.VISIBLE);
                    vehicleKmEditText2.setVisibility(View.VISIBLE);
                    fuelTypeSpinner2.setVisibility(View.VISIBLE);
                    full2.setVisibility(View.VISIBLE);

                    LinearLayout linearLayout = findViewById(R.id.repostajeDetailsLayout);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                    linearLayout.setLayoutParams(layoutParams);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "¡Por favor, selecciona una opción!", Toast.LENGTH_SHORT).show();
            }
        });

        doubleRefuel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    eurosRepostadosEditText2.setEnabled(true);
                    fuelPriceEditText2.setEnabled(true);
                    vehicleKmEditText2.setEnabled(true);
                    fuelTypeSpinner2.setEnabled(true);
                    full2.setEnabled(true);

                } else {
                    eurosRepostadosEditText2.setEnabled(false);
                    fuelPriceEditText2.setEnabled(false);
                    vehicleKmEditText2.setEnabled(false);
                    fuelTypeSpinner2.setEnabled(false);
                    full2.setEnabled(false);

                }
            }
        });



        stationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                List<StationDTO> stations = (List<StationDTO>) stationSpinner.getTag();

                StationDTO selectedStation = stations.get(position);

                stationId = selectedStation.getId();
                stationName = selectedStation.getName();
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

            if (selectedPosition == 0 && totalPositions > 1){
                Refuel refuel = new Refuel();


                if (eurosRepostadosEditText.getText().toString().isEmpty() ||
                        fuelPriceEditText.getText().toString().isEmpty() ||
                        vehicleKmFuel1EditText.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterRefuelView.this, "Por favor, complete todos los campos obligatorios.", Toast.LENGTH_LONG).show();
                    return;
                }


                float amount = Float.parseFloat(eurosRepostadosEditText.getText().toString());
                float fuelPrice = Float.parseFloat(fuelPriceEditText.getText().toString());
                int kmFuel = Integer.parseInt(vehicleKmFuel1EditText.getText().toString());
                String fuelType = fuelTypeSpinner.getSelectedItem().toString();

                String validationError = validateRefuelData(amount, fuelPrice, kmFuel);
                if (validationError != null) {
                    Toast.makeText(RegisterRefuelView.this, validationError, Toast.LENGTH_LONG).show();
                    return;
                }




                refuel.setFuel(fuelType);
                refuel.setAmount(amount);
                refuel.setPrice(fuelPrice);
                refuel.setKmTraveled1(kmFuel);

                float amount2 = 0, fuelPrice2 = 0;
                int vehicleKm2 = 0;
                String fuelType2 = "";
                boolean fulled2 = false;

                if (doubleRefuel.isChecked()) {



                    if (eurosRepostadosEditText2.getText().toString().isEmpty() ||
                            fuelPriceEditText2.getText().toString().isEmpty() ||
                            vehicleKmEditText2.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Por favor, complete todos los campos del segundo repostaje.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    amount2 = Float.parseFloat(eurosRepostadosEditText2.getText().toString());
                    fuelPrice2 = Float.parseFloat(fuelPriceEditText2.getText().toString());
                    vehicleKm2 = Integer.parseInt(vehicleKmEditText2.getText().toString());
                    fuelType2 = fuelTypeSpinner2.getSelectedItem().toString();
                    fulled2 = full2.isChecked();

                    String validationError2 = validateRefuelData(amount2, fuelPrice2, vehicleKm2);
                    if (validationError2 != null) {
                        Toast.makeText(RegisterRefuelView.this, validationError, Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Solo asignar los valores del segundo repostaje si está habilitado
                    refuel.setSecondFuel(fuelType2);
                    refuel.setSecondAmount(amount2);
                    refuel.setSecondPrice(fuelPrice2);
                    refuel.setKmsTraveledSecondrefuel(vehicleKm2);
                    refuel.setSecondFulled(fulled2);
                } else {

                    refuel.setSecondFuel(null);
                    refuel.setSecondAmount(0);
                    refuel.setSecondPrice(0);
                    refuel.setKmsTraveledSecondrefuel(0);
                    refuel.setSecondFulled(false);
                }



                refuel.setUserId(userId);
                refuel.setNameStation(stationName);
                refuel.setNameVehicle(license);
                refuel.setFulled(full.isChecked());
                refuel.setDoubleRefuel(doubleRefuel.isChecked());



                presenter.insertRefuel(vehicleId, stationId, refuel);
                Intent intent = new Intent(RegisterRefuelView.this, MainMenu.class);
                startActivity(intent);
                finish();

            } else if (selectedPosition == 0 && totalPositions == 1){
                Refuel refuel = new Refuel();




                if (eurosRepostadosEditText.getText().toString().isEmpty() ||
                        fuelPriceEditText.getText().toString().isEmpty() ||
                        vehicleKmFuel1EditText.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterRefuelView.this, "Por favor, complete todos los campos obligatorios.", Toast.LENGTH_LONG).show();
                    return;
                }


                float amount = Float.parseFloat(eurosRepostadosEditText.getText().toString());
                float fuelPrice = Float.parseFloat(fuelPriceEditText.getText().toString());
                int kmFuel = Integer.parseInt(vehicleKmFuel1EditText.getText().toString());
                String fuelType = fuelTypeSpinner.getSelectedItem().toString();

                String validationError = validateRefuelData(amount, fuelPrice, kmFuel);
                if (validationError != null) {
                    Toast.makeText(RegisterRefuelView.this, validationError, Toast.LENGTH_LONG).show();
                    return;
                }



                refuel.setFuel(fuelType);
                refuel.setAmount(amount);
                refuel.setPrice(fuelPrice);
                refuel.setKmTraveled1(kmFuel);


                refuel.setSecondFuel(null);
                refuel.setSecondAmount(0);
                refuel.setSecondPrice(0);
                refuel.setKmsTraveledSecondrefuel(0);
                refuel.setSecondFulled(false);

                refuel.setUserId(userId);
                refuel.setNameStation(stationName);
                refuel.setNameVehicle(license);
                refuel.setFulled(full.isChecked());
                refuel.setDoubleRefuel(doubleRefuel.isChecked());




                presenter.insertRefuel(vehicleId, stationId, refuel);
                Intent intent = new Intent(RegisterRefuelView.this, MainMenu.class);
                startActivity(intent);
                finish();

            } else if (selectedPosition == 1){
                Refuel refuel = new Refuel();

                doubleRefuel.setVisibility(View.INVISIBLE);
                textView7.setVisibility(View.INVISIBLE);
                textView8.setVisibility(View.INVISIBLE);
                textView9.setVisibility(View.INVISIBLE);
                textView10.setVisibility(View.INVISIBLE);
                textView11.setVisibility(View.INVISIBLE);
                eurosRepostadosEditText2.setVisibility(View.INVISIBLE);
                fuelPriceEditText2.setVisibility(View.INVISIBLE);
                vehicleKmEditText2.setVisibility(View.INVISIBLE);
                fuelTypeSpinner2.setVisibility(View.INVISIBLE);
                full2.setVisibility(View.INVISIBLE);




                if (eurosRepostadosEditText.getText().toString().isEmpty() ||
                        fuelPriceEditText.getText().toString().isEmpty() ||
                        vehicleKmFuel1EditText.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterRefuelView.this, "Por favor, complete todos los campos obligatorios.", Toast.LENGTH_LONG).show();
                    return;
                }


                float amount = Float.parseFloat(eurosRepostadosEditText.getText().toString());
                float fuelPrice = Float.parseFloat(fuelPriceEditText.getText().toString());
                int kmFuel = Integer.parseInt(vehicleKmFuel1EditText.getText().toString());
                String fuelType = fuelTypeSpinner.getSelectedItem().toString();

                String validationError = validateRefuelData(amount, fuelPrice, kmFuel);
                if (validationError != null) {
                    Toast.makeText(RegisterRefuelView.this, validationError, Toast.LENGTH_LONG).show();
                    return;
                }

                refuel.setFuel(null);
                refuel.setAmount(0);
                refuel.setPrice(0);
                refuel.setKmTraveled1(0);
                refuel.setFulled(false);


                refuel.setSecondFuel(fuelType);
                refuel.setSecondAmount(amount);
                refuel.setSecondPrice(fuelPrice);
                refuel.setKmsTraveledSecondrefuel(kmFuel);
                refuel.setSecondFulled(full.isChecked());

                refuel.setUserId(userId);
                refuel.setNameStation(stationName);
                refuel.setNameVehicle(license);
                refuel.setDoubleRefuel(doubleRefuel.isChecked());


                presenter.insertRefuel(vehicleId, stationId, refuel);


                Intent intent = new Intent(RegisterRefuelView.this, MainMenu.class);
                startActivity(intent);
                finish();

            }


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
                "<b>Kilómetros actuales del vehículo:</b> " + vehicleKmFuel1EditText.getText().toString() + "<br>" +
                "<b>Llenado de depósito:</b> " + llenado;

        builder.setMessage(Html.fromHtml(message));



        builder.setPositiveButton("Sí", (dialog, which) -> {
            registerRefuel(create_button);

        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        // Mostrar el aviso
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void confirmRegisterKms() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERTA, CONFIRMACIÓN DE KILÓMETROS");
        String message = "<b>Los kilómetros actuales del vehículo, superan los 1500Kms desde el anterior repostaje. </b>" + "<br>" +
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


    public interface OnFuel2CheckListener {
        void onFuel2CheckComplete(boolean hasFuel2);
    }

    private String validateRefuelData(float amount, float fuelPrice, int kmFuel) {
        if (amount <= 0 || amount > 200) {
            return "El importe debe estar entre 0 y 200.";
        }
        if (fuelPrice <= 0 || fuelPrice > 2) {
            return "El precio del combustible debe estar entre 0 y 2.";
        }
        if (kmFuel <= 0 || kmFuel > 1500) {
            return "Los kilómetros deben estar entre 0 y 1500.";
        }
        return null;
    }




}