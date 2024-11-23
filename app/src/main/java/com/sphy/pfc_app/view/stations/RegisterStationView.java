package com.sphy.pfc_app.view.stations;

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
import com.sphy.pfc_app.contract.stations.StationRegisterContract;
import com.sphy.pfc_app.contract.vehicles.VehicleRegisterContract;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.presenter.Stations.StationRegisterPresenter;
import com.sphy.pfc_app.presenter.vehicles.VehicleRegisterPresenter;
import com.sphy.pfc_app.view.BaseActivity;

public class RegisterStationView extends BaseActivity implements StationRegisterContract.View{

    private ImageButton menuButton;
    private Button create_button;
    private StationRegisterPresenter presenter;

    private EditText etStation_name;
    private EditText etAddress;
    private EditText etSite;
    private Spinner provinceSpinner;
    private CheckBox ckStation_glp;

    private TextView username;
    private Button backButton;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_station);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        menuButton = findViewById(R.id.menuButton);
        create_button = findViewById(R.id.register_station_button);
        setupMenuButton(menuButton);
        username = findViewById(R.id.userNameTextView);

        presenter = new StationRegisterPresenter(this);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user);

        etStation_name = findViewById((R.id.station_name));
        etAddress = findViewById(R.id.station_address);
        etSite = findViewById(R.id.station_site);
        provinceSpinner = findViewById(R.id.province_spinner);
        ckStation_glp = findViewById(R.id.station_glp);

        SpinnerAdapter.populateProvinceSpinner(this,provinceSpinner);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMain(v);
            }
        });

    }

    public void registerStation(View view) {

        String stationName = etStation_name.getText().toString();
        String address = etAddress.getText().toString();
        String site = etSite.getText().toString();
        String province = provinceSpinner.getSelectedItem().toString();
        boolean glp = ckStation_glp.isChecked();

        Station station = new Station();
        station.setName(stationName);
        station.setAddress(address);
        station.setSite(site);

        if (province.equals("Elige una de la lista...")){
            station.setProvince("No definida");
        }else {
            station.setProvince(province);
        }

        station.setGlpFuel(glp);

        presenter.insertStation(station);

    }

    @Override
    public void showInsertSuccessMessage() {
        Toast.makeText(this,"Estación añadida", Toast.LENGTH_LONG).show();
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
