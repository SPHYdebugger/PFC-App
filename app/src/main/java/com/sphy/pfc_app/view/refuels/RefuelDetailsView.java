package com.sphy.pfc_app.view.refuels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.refuels.RefuelDetailsContract;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.presenter.Refuels.RefuelDetailsPresenter;
import com.sphy.pfc_app.presenter.vehicles.VehicleDetailsPresenter;
import com.sphy.pfc_app.view.BaseActivity;


public class RefuelDetailsView extends BaseActivity implements RefuelDetailsContract.View {

    private TextView tvVehicleLicense;
    private TextView tvStationName;
    private TextView tvRegisterDate;
    private TextView tvAmount;

    private TextView tvFuel;

    private TextView tvPrice;
    private TextView tvRefueledLiters;
    private TextView tvKmsVehicle;

    private TextView tvKmsTraveled;

    private TextView tvRefuelConsumption;
    private TextView tvVehicleConsumption;
    private TextView tvDetalleDE;

    private CheckBox cbFulled;






    private RefuelDetailsContract.Presenter presenter;


    private long refuelId;

    private ImageButton menuButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_refuel);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        tvDetalleDE = findViewById(R.id.detalleDE);

        tvRegisterDate = findViewById(R.id.detail_date);
        tvVehicleLicense = findViewById(R.id.detail_vehicle);
        tvStationName = findViewById(R.id.detail_station);
        tvAmount = findViewById(R.id.detail_amount);
        tvPrice = findViewById(R.id.detail_price);
        tvFuel = findViewById(R.id.detail_fuel);
        tvRefueledLiters = findViewById(R.id.detail_liters);
        tvKmsTraveled = findViewById(R.id.detail_kmsTraveled);
        tvKmsVehicle = findViewById(R.id.detail_kms);
        tvRefuelConsumption = findViewById(R.id.detail_refuelConsumption);
        tvVehicleConsumption = findViewById(R.id.detail_consum);
        cbFulled = findViewById(R.id.fulled);

        presenter = new RefuelDetailsPresenter(this);

        Intent intent = getIntent();
        refuelId = intent.getLongExtra("refuelId", 0);
        if (refuelId == 0){
            System.out.println("no se ha obtenido el refuelId");
        }else {
            String refuelIdString = String.valueOf(refuelId);
            System.out.println("valor que se recoge " + refuelIdString);
            presenter.getRefuel(refuelIdString);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        refuelId = intent.getLongExtra("refuelId", 0);
        if (refuelId == 0){
            System.out.println("no se ha obtenido el refuelId");
        }else {
            String refuelIdString = String.valueOf(refuelId);
            System.out.println("valor que se recoge en String" + refuelIdString);
            presenter.getRefuel(refuelIdString);
        }

    }

    @Override
    public void displayRefuelDetails(Refuel refuel) {

        String text = "DETALLE DEL REPOSTAJE: " + refuel.getCreationDate();
        tvDetalleDE.setText(text);

        tvRegisterDate.setText(refuel.getCreationDate());
        tvVehicleLicense.setText(refuel.getNameVehicle());
        tvStationName.setText(refuel.getNameStation());
        tvAmount.setText(String.valueOf(refuel.getAmount()));
        tvPrice.setText(String.valueOf(refuel.getPrice()));
        tvFuel.setText(refuel.getFuel());
        tvRefueledLiters.setText(String.valueOf(refuel.getRefueledLiters()));
        tvKmsTraveled.setText(String.valueOf(refuel.getKmTraveled()));
        //tvKmsVehicle.setText(String.valueOf(refuel.getKmTotal()));
        tvRefuelConsumption.setText(String.valueOf(refuel.getRefuelConsumption()));
        tvVehicleConsumption.setText(String.valueOf(refuel.getMedConsumption()));
        cbFulled.setChecked(refuel.isFulled());

    }

}