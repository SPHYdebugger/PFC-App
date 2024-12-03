package com.sphy.pfc_app.view.vehicles;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.adapter.VehicleDTOAdapter;
import com.sphy.pfc_app.api.RefuelApi;
import com.sphy.pfc_app.api.RefuelApiInterface;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.presenter.vehicles.VehicleListPresenter;
import com.sphy.pfc_app.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleListView extends BaseActivity implements VehicleListContract.View {

    private List<VehicleDTO> vehicles;


    private VehicleDTOAdapter adapter;
    private VehicleListPresenter presenter;

    private ImageButton menuButton;
    private Button backButton;

    private TextView username;

    private SharedPreferencesManager sharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vehicles);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        menuButton = findViewById(R.id.menuButton);
        setupMenuButton(menuButton);
        backButton = findViewById(R.id.backButton);
        username = findViewById(R.id.userNameTextView);

        presenter = new VehicleListPresenter(this);

        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);
        username.setText(user.toUpperCase());

        vehicles = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.vehicles_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new VehicleDTOAdapter(VehicleListView.this, vehicles);
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
        presenter.loadAllVehicles();
    }

    @Override
    public void listVehicles(List<VehicleDTO> vehicles) {
        this.vehicles.clear();
        this.vehicles.addAll(vehicles);

        for (VehicleDTO vehicle : vehicles) {
            calculateRealConsumptionsForVehicle(vehicle);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Notificar al adapter que los datos han cambiado
                adapter.notifyDataSetChanged();
            }
        }, 2000);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void backMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void calculateRealConsumptionsForVehicle(VehicleDTO vehicle) {
        RefuelApiInterface api = RefuelApi.buildInstance(this);
        Call<List<Refuel>> getRefuelsCall = api.findRefuelByIdentifier(vehicle.getLicensePlate());

        getRefuelsCall.enqueue(new Callback<List<Refuel>>() {
            @Override
            public void onResponse(Call<List<Refuel>> call, Response<List<Refuel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Refuel> refuels = response.body();

                    // Calcular consumos reales para ambos tipos de combustible
                    float totalRealConsumption1 = 0f;
                    float totalRealConsumption2 = 0f;
                    int countRealConsumption1 = 0;
                    int countRealConsumption2 = 0;

                    // Iterar sobre los repostajes
                    for (int i = 1; i < refuels.size(); i++) {
                        Refuel refuel = refuels.get(i);

                        // Calcular consumo real del primer combustible
                        if (refuel.isFulled() && refuels.get(i - 1).isFulled()) {
                            totalRealConsumption1 += refuel.getRefuelConsumption();
                            countRealConsumption1++;
                        }

                        // Calcular consumo real del segundo combustible
                        if (refuel.isSecondFulled() && refuels.get(i - 1).isSecondFulled()) {
                            totalRealConsumption2 += refuel.getSecondRefuelConsumption();
                            countRealConsumption2++;
                        }
                    }

                    // Calcular los promedios
                    if (countRealConsumption1 > 0) {
                        vehicle.setRealConsumption1(totalRealConsumption1 / countRealConsumption1);
                    } else {
                        vehicle.setRealConsumption1(0f);
                    }

                    if (countRealConsumption2 > 0) {
                        vehicle.setRealConsumption2(totalRealConsumption2 / countRealConsumption2);
                    } else {
                        vehicle.setRealConsumption2(0f);
                    }

                    System.out.println("Valor de realCons1 =" + vehicle.getRealConsumption1());
                    System.out.println("Valor de realCons2 =" + vehicle.getRealConsumption2());

                } else {
                    Log.e("VehicleListView", "Error al obtener repostajes para el vehículo: " + vehicle.getLicensePlate());
                }
            }

            @Override
            public void onFailure(Call<List<Refuel>> call, Throwable t) {
                Log.e("VehicleListView", "Error al obtener repostajes para el vehículo: " + t.getMessage());
            }
        });
    }

}

