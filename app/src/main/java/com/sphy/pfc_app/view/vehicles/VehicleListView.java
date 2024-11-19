package com.sphy.pfc_app.view.vehicles;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.VehicleDTOAdapter;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.presenter.vehicles.VehicleListPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;
public class VehicleListView extends BaseActivity implements VehicleListContract.View {

    private List<VehicleDTO> vehicles;
    private VehicleDTOAdapter adapter;
    private VehicleListPresenter presenter;

    private ImageButton menuButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vehicles);

        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        backButton = findViewById(R.id.backButton);

        presenter = new VehicleListPresenter(this);  // Inicializamos el Presenter

        vehicles = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.vehicles_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new VehicleDTOAdapter(vehicles);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAllVehicles();  // Llamamos al presenter para cargar los vehículos
    }

    @Override
    public void listVehicles(List<VehicleDTO> vehicles) {
        this.vehicles.clear();
        this.vehicles.addAll(vehicles);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
