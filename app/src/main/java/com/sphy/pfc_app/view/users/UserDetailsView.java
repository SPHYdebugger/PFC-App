package com.sphy.pfc_app.view.users;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.MainMenu;
import com.sphy.pfc_app.R;
import com.sphy.pfc_app.api.RefuelApi;
import com.sphy.pfc_app.api.RefuelApiInterface;
import com.sphy.pfc_app.api.StationApi;
import com.sphy.pfc_app.api.StationApiInterface;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.refuels.RefuelListContract;
import com.sphy.pfc_app.contract.stations.StationListContract;
import com.sphy.pfc_app.contract.vehicles.SelectionVehicleListContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.login.SharedPreferencesManager;
import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsView extends BaseActivity {

    private TextView detailName;
    private TextView detailRegisterDate;
    private TextView detailNumberVehicles;
    private TextView detailNumberStations;
    private TextView detailNumberRefuels;
    private TextView detailMostUsedVehicle;
    private TextView detailMostUsedStation;
    private List<VehicleDTO> vehicleList = new ArrayList<>();
    private List<StationDTO> stationList = new ArrayList<>();
    private List<Refuel> refuelList = new ArrayList<>();
    private TextView username;
    private ImageButton menuButton;
    private Button backButton;

    private boolean vehiclesLoaded = false;
    private boolean stationsLoaded = false;
    private boolean refuelsLoaded = false;
    private int totalRefuelsToLoad = 0;  // Contador de repostajes por cargar
    private int refuelsLoadedCount = 0;   // Contador de repostajes cargados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_user);

        menuButton = findViewById(R.id.menuButton);
        backButton = findViewById(R.id.backButton);
        detailName = findViewById(R.id.detail_name);
        detailRegisterDate = findViewById(R.id.detail_registerDate);
        detailNumberVehicles = findViewById(R.id.detail_vehicles);
        detailNumberStations = findViewById(R.id.detail_stations);
        detailNumberRefuels = findViewById(R.id.detail_lastRefuels);
        detailMostUsedVehicle = findViewById(R.id.detail_mostUsedVehicle);
        detailMostUsedStation = findViewById(R.id.detail_mostUsedStation);
        username = findViewById(R.id.userNameTextView);

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        String token = sharedPreferencesManager.getAuthToken();
        String user = sharedPreferencesManager.getUsernameFromJWT(token);

        username.setText(user.toUpperCase());
        long id = sharedPreferencesManager.getUserIdFromJWT(token);
        detailName.setText(user);
        detailRegisterDate.setText("20-11-2024");
        setupMenuButton(menuButton);

        loadUserData(id);
        backButton.setOnClickListener(v -> backMain(v));
    }

    private void loadUserData(long userId) {
        // Obtener los vehículos del usuario
        getUserVehicles(userId, new SelectionVehicleListContract.Model.OnLoadVehicleListener() {
            @Override
            public void onLoadVehiclesSuccess(List<VehicleDTO> vehicles) {
                vehicleList = vehicles;
                Log.e("UserDetailsView", "Vehículos obtenidos: " + vehicleList.size());

                vehiclesLoaded = true;
                detailNumberVehicles.setText(String.valueOf(vehicleList.size()));

                totalRefuelsToLoad = vehicleList.size();  // Establecer cuántos repostajes necesitamos cargar
                refuelsLoadedCount = 0;  // Reiniciar el contador de repostajes cargados

                // Cargar los repostajes de cada vehículo
                for (VehicleDTO vehicle : vehicleList) {
                    getRefuelsForVehicle(vehicle.getLicensePlate());
                }

                checkDataLoaded();
            }

            @Override
            public void onLoadVehiclesError(String errorMessage) {
                Log.e("UserDetailsView", "Error obteniendo vehículos: " + errorMessage);
            }
        });

        // Obtener las estaciones del usuario
        getUserStations(userId, new StationListContract.Model.OnLoadStationListener() {
            @Override
            public void onLoadStationsSuccess(List<StationDTO> stations) {
                stationList = stations;
                Log.e("UserDetailsView", "Estaciones obtenidas: " + stationList.size());
                detailNumberStations.setText(String.valueOf(stationList.size()));

                stationsLoaded = true;
                checkDataLoaded();
            }

            @Override
            public void onLoadStationsError(String errorMessage) {
                Log.e("UserDetailsView", "Error obteniendo estaciones: " + errorMessage);
            }
        });
    }

    // Método para obtener los vehículos del usuario
    public void getUserVehicles(long userId, SelectionVehicleListContract.Model.OnLoadVehicleListener listener) {
        VehicleApiInterface api = VehicleApi.buildInstance(this);
        Call<List<VehicleDTO>> getVehiclesCall = api.getVehiclesByUserId(userId);
        getVehiclesCall.enqueue(new Callback<List<VehicleDTO>>() {
            @Override
            public void onResponse(Call<List<VehicleDTO>> call, Response<List<VehicleDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Filtrar vehículos con hide = false
                    List<VehicleDTO> visibleVehicles = new ArrayList<>();
                    for (VehicleDTO vehicle : response.body()) {
                        if (!vehicle.isHide()) {
                            visibleVehicles.add(vehicle);
                        }
                    }
                    listener.onLoadVehiclesSuccess(visibleVehicles);
                } else {
                    listener.onLoadVehiclesError("Error al cargar vehículos");
                }
            }

            @Override
            public void onFailure(Call<List<VehicleDTO>> call, Throwable t) {
                listener.onLoadVehiclesError(t.getMessage());
            }
        });
    }

    // Método para obtener las estaciones del usuario
    public void getUserStations(long userId, StationListContract.Model.OnLoadStationListener listener) {
        StationApiInterface api = StationApi.buildInstance(this);
        Call<List<StationDTO>> getStationsCall = api.getStationsByUserId(userId);
        getStationsCall.enqueue(new Callback<List<StationDTO>>() {
            @Override
            public void onResponse(Call<List<StationDTO>> call, Response<List<StationDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onLoadStationsSuccess(response.body());
                } else {
                    listener.onLoadStationsError("Error al cargar estaciones");
                }
            }

            @Override
            public void onFailure(Call<List<StationDTO>> call, Throwable t) {
                listener.onLoadStationsError(t.getMessage());
            }
        });
    }

    // Método para obtener los repostajes para un vehículo
    public void getRefuelsForVehicle(String vehicleIdentifier) {
        RefuelApiInterface api = RefuelApi.buildInstance(this);
        Call<List<Refuel>> getRefuelsCall = api.findRefuelByIdentifier(vehicleIdentifier);
        getRefuelsCall.enqueue(new Callback<List<Refuel>>() {
            @Override
            public void onResponse(Call<List<Refuel>> call, Response<List<Refuel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Refuel> refuels = response.body();
                    for (Refuel refuel : refuels) {
                        Log.d("UserDetailsView", "Refuel: " + refuel.toString());
                    }
                    refuelList.addAll(refuels);
                    Log.e("UserDetailsView", "Repostajes obtenidos para el vehículo " + vehicleIdentifier + ": " + refuels.size());
                    detailNumberRefuels.setText(String.valueOf(refuelList.size()));

                    refuelsLoadedCount++;  // Incrementar el contador de repostajes cargados

                    // Verificar si todos los repostajes han sido cargados
                    checkDataLoaded();  // Verificar si todos los repostajes están cargados
                } else {
                    Log.e("UserDetailsView", "Error al obtener repostajes para el vehículo: " + vehicleIdentifier);
                }
            }

            @Override
            public void onFailure(Call<List<Refuel>> call, Throwable t) {
                Log.e("UserDetailsView", "Error al obtener repostajes: " + t.getMessage());
            }
        });
    }

    private void checkDataLoaded() {
        // Verificar si todos los datos están listos
        if (vehiclesLoaded && stationsLoaded && refuelsLoadedCount == totalRefuelsToLoad) {

            updateMostUsedVehicle();
            updateMostUsedStation();
        }
    }

    private void updateMostUsedVehicle() {
        if (vehicleList.isEmpty() || refuelList.isEmpty()) {
            return;
        }

        // Mapa que almacena el número de repostajes por matrícula del vehículo
        Map<String, Integer> vehicleCountMap = new HashMap<>();

        // Contamos los repostajes por cada vehículo (según la matrícula)
        for (Refuel refuel : refuelList) {
            String vehicleName = refuel.getNameVehicle();  // Suponemos que 'nameVehicle' es la matrícula
            vehicleCountMap.put(vehicleName, vehicleCountMap.getOrDefault(vehicleName, 0) + 1);
        }

        // Buscar la matrícula del vehículo con más repostajes
        String mostUsedVehicleName = vehicleCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())  // Encontrar la matrícula con más repostajes
                .map(Map.Entry::getKey)
                .orElse(null);

        if (mostUsedVehicleName != null) {
            // Buscamos el vehículo con esa matrícula y mostramos su placa
            vehicleList.stream()
                    .filter(vehicle -> vehicle.getLicensePlate().equals(mostUsedVehicleName)) // Usamos 'equals' para comparar cadenas
                    .findFirst()
                    .ifPresent(vehicle -> detailMostUsedVehicle.setText(vehicle.getLicensePlate())); // Mostrar la placa
        }
    }




    private void updateMostUsedStation() {
        if (stationList.isEmpty() || refuelList.isEmpty()) {
            return;
        }

        // Mapa que almacena el número de repostajes por nombre de estación
        Map<String, Integer> stationCountMap = new HashMap<>();

        // Contamos los repostajes por cada estación (según el nombre de la estación)
        for (Refuel refuel : refuelList) {
            String stationName = refuel.getNameStation();  // Suponemos que 'nameStation' es el nombre de la estación
            stationCountMap.put(stationName, stationCountMap.getOrDefault(stationName, 0) + 1);
        }

        // Buscar el nombre de la estación con más repostajes
        String mostUsedStationName = stationCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())  // Encontrar el nombre de la estación con más repostajes
                .map(Map.Entry::getKey)
                .orElse(null);

        if (mostUsedStationName != null) {
            // Buscamos la estación con ese nombre y mostramos su nombre
            for (StationDTO station : stationList) {
                if (station.getName().equals(mostUsedStationName)) {
                    detailMostUsedStation.setText(station.getName());
                    break;
                }
            }
        }
    }


    public void backMain(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
