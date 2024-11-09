package com.sphy.pfc_app.view.vehicles;



import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.VehicleDTOAdapter;
import com.sphy.pfc_app.adapter.VehicleSelectionAdapter;
import com.sphy.pfc_app.contract.vehicles.SelectionVehicleListContract;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.presenter.vehicles.SelectionVehicleListPresenter;
import com.sphy.pfc_app.presenter.vehicles.VehicleListPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectionVehicleListView extends BaseActivity implements SelectionVehicleListContract.View {

    private List<VehicleDTO> vehicles;
    private VehicleSelectionAdapter adapter;
    private SelectionVehicleListPresenter presenter;

    private ImageButton menuButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_vehicles);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);

        presenter = new SelectionVehicleListPresenter(this);



        vehicles = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.vehicles_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new VehicleSelectionAdapter(vehicles);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.loadAllVehicles();


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


}