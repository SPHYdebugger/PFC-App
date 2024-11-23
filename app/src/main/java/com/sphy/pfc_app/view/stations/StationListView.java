package com.sphy.pfc_app.view.stations;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.StationDTOAdapter;
import com.sphy.pfc_app.adapter.VehicleDTOAdapter;
import com.sphy.pfc_app.contract.stations.StationListContract;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.login.SharedPreferencesManager;
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

    private TextView username;
    private Button backButton;

    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stations);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        username = findViewById(R.id.userNameTextView);
        backButton = findViewById(R.id.backButton);
        presenter = new StationListPresenter(this);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user);

        stations = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.stations_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StationDTOAdapter(stations);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMain(v);
            }
        });
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

    public void backMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}