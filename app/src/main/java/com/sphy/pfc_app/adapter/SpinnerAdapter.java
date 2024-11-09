package com.sphy.pfc_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.api.StationApi;
import com.sphy.pfc_app.api.StationApiInterface;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpinnerAdapter {

    public static void populateFuelTypeSpinner(Context context, Spinner fuelTypeSpinner, long vehicleId) {

        VehicleApiInterface api = VehicleApi.buildInstance();
        Call<Vehicle> vehicleSelected = api.getVehicleById(vehicleId);

        vehicleSelected.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Vehicle vehicle = response.body();

                    List<String> fuelTypes = new ArrayList<>();
                    if (vehicle.getFuel1() != null && !vehicle.getFuel1().isEmpty()) {
                        fuelTypes.add(vehicle.getFuel1());
                    }

                    if (vehicle.getFuel2() != null && !vehicle.getFuel2().isEmpty()) {
                        fuelTypes.add(vehicle.getFuel2());
                    }


                    ArrayAdapter<String> fuelTypeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, fuelTypes);
                    fuelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    fuelTypeSpinner.post(() -> fuelTypeSpinner.setAdapter(fuelTypeAdapter));

                } else {
                    Log.e("Vehicle", "Error al obtener el vehículo: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Log.e("hideVehicle", "Error al conectar con el servidor para obtener el vehículo: " + t.getMessage());
            }
        });
    }

    public static void populateStationSpinner(Context context, Spinner stationSpinner) {
        StationApiInterface api = StationApi.buildInstance();
        Call<List<StationDTO>> stationsCall = api.getStations();

        stationsCall.enqueue(new Callback<List<StationDTO>>() {
            @Override
            public void onResponse(Call<List<StationDTO>> call, Response<List<StationDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<StationDTO> stations = response.body();
                    StationAdapter stationAdapter = new StationAdapter(context, stations);

                    stationSpinner.setAdapter(stationAdapter);
                    stationSpinner.setTag(stations);
                } else {
                    Log.e("Stations", "Error al obtener las estaciones: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<StationDTO>> call, Throwable t) {
                Log.e("Stations", "Error al conectar con el servidor para obtener las estaciones: " + t.getMessage());
            }
        });
    }

    public static void populateProvinceSpinner(Context context, Spinner provinceSpinner) {

        String[] provinces = {
                "Elige una de la lista...", "A Coruña", "Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila", "Badajoz",
                "Baleares", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Cantabria", "Castellón", "Ciudad Real",
                "Córdoba", "Cuenca", "Girona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca",
                "Jaén", "La Rioja", "Las Palmas", "León", "Lleida", "Lugo", "Madrid", "Málaga", "Murcia",
                "Navarra", "Ourense", "Palencia", "Pontevedra", "Salamanca", "Segovia", "Sevilla",
                "Soria", "Tarragona", "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia",
                "Valladolid", "Vizcaya", "Zamora", "Zaragoza"
        };

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, provinces);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        provinceSpinner.setAdapter(provinceAdapter);
    }




}

