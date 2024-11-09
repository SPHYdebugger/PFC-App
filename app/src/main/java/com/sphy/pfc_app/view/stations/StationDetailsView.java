package com.sphy.pfc_app.view.stations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.contract.stations.StationDetailsContract;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.presenter.Stations.StationDetailsPresenter;
import com.sphy.pfc_app.presenter.vehicles.VehicleDetailsPresenter;
import com.sphy.pfc_app.view.BaseActivity;
import com.sphy.pfc_app.view.refuels.RefuelListView;


public class StationDetailsView extends BaseActivity implements StationDetailsContract.View {

    private TextView tvStationId;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvSite;

    private TextView tvProvince;

    private TextView tvGlp;
    private TextView tvRefuels;
    private TextView tvDetalleDE;

    private Button goRefuels;




    private StationDetailsContract.Presenter presenter;
    private long stationId;

    private String nameGet;

    private ImageButton menuButton;

    private Station temporalStation;
    private int stationRefuels;
    private long refuelsLong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_station);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        tvDetalleDE = findViewById(R.id.detalleDE);
        tvStationId = findViewById(R.id.detail_stationId);
        tvName = findViewById(R.id.detail_name);
        tvAddress = findViewById(R.id.detail_address);
        tvSite = findViewById(R.id.detail_site);
        tvProvince = findViewById(R.id.detail_province);
        tvGlp = findViewById(R.id.detail_glp);
        tvRefuels = findViewById(R.id.detail_refuels);
        goRefuels = findViewById(R.id.goRefuels);


        presenter = new StationDetailsPresenter(this);

        Intent intent = getIntent();
        stationId = intent.getLongExtra("Id", stationId);
        nameGet = intent.getStringExtra("name");
        System.out.println("valor que se recoge " + nameGet);
        System.out.println("valor id que se recoge " + stationId);
        presenter.getStation(stationId);


        goRefuels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goStationRefuels();
            }
        });
    }

    private void goStationRefuels() {
        Intent intent = new Intent(StationDetailsView.this, RefuelListView.class);
        System.out.println("valor que se pasa " + nameGet);
        intent.putExtra("name", nameGet);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        stationId = intent.getLongExtra("stationId",stationId);
        presenter.getStation(stationId);
    }

    @Override
    public void displayStationDetails(StationDTO station) {

        String text = "DETALLE DE LA ESTACIÓN: " + station.getName();
        tvDetalleDE.setText(text);
        tvStationId.setText(String.valueOf(station.getId()));
        tvName.setText(station.getName());
        tvAddress.setText(station.getAddress());
        tvSite.setText(station.getSite());
        tvProvince.setText(station.getProvince());
        String glpOption;
        if (station.isGlpFuel()){
            glpOption = "SI";
        } else {
            glpOption = "NO";
        }
        tvGlp.setText(glpOption);
        tvRefuels.setText(String.valueOf(station.getRefuels()));
    }

    @Override
    public void showUpdateSuccessMessage() {
        Toast.makeText(this, "Estación modificada", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUpdateErrorMessage() {
        Toast.makeText(this, "Error al actualizar la estación", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteSuccessMessage() {
        Toast.makeText(this, "Estación eliminada correctamente", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showDeleteErrorMessage() {
        Toast.makeText(this, "Error al eliminar la estación", Toast.LENGTH_LONG).show();
    }



    private void confirmDeleteStation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERTA, CONFIRMACIÓN");
        builder.setMessage("¿EStá seguro de querer borrar la estación?");

        // Botón confirmar
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteStation(stationId);
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