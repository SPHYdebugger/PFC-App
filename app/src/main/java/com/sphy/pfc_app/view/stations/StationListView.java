package com.sphy.pfc_app.view.stations;



import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.StationDTOAdapter;
import com.sphy.pfc_app.adapter.VehicleDTOAdapter;
import com.sphy.pfc_app.contract.stations.StationListContract;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.presenter.Stations.StationListPresenter;
import com.sphy.pfc_app.presenter.vehicles.VehicleListPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class StationListView extends BaseActivity implements StationListContract.View {

    private List<StationDTO> stations;
    private StationDTOAdapter adapter;
    private StationListPresenter presenter;

    private ImageButton menuButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stations);
        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);

        presenter = new StationListPresenter(this);



        stations = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.stations_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StationDTOAdapter(stations);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.loadAllStations();


    }


    @Override
    public void listStations(List<StationDTO> stations) {
        this.stations.clear();
        this.stations.addAll(stations);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


}