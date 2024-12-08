package com.sphy.pfc_app.model.vehicles;

import android.content.Context;

import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.vehicles.UpdateVehicleContract;
import com.sphy.pfc_app.domain.Vehicle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVehicleModel implements UpdateVehicleContract.Model {

    private final Context context;

    public UpdateVehicleModel(Context context) {
        this.context = context;
    }

    @Override
    public void fetchVehicleDetails(long vehicleId, OnFinishedListener listener) {
        VehicleApiInterface api = VehicleApi.buildInstance(context);
        Call<Vehicle> getVehicleCall = api.getVehicleById(vehicleId);
        getVehicleCall.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure("Error al obtener los detalles del vehículo");
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                listener.onFailure("Error al conectar con el servidor: " + t.getMessage());
            }
        });
    }

    @Override
    public void updateVehicleDetails(Vehicle vehicle, OnFinishedListener listener) {
        System.out.println("llega a realizar la petición al model");
        VehicleApiInterface api = VehicleApi.buildInstance(context);
        System.out.println("Vehículo que se va a enviar para actualización:");
        System.out.println("Matrícula: " + vehicle.getLicensePlate());
        System.out.println("Marca: " + vehicle.getBrand());
        System.out.println("Modelo: " + vehicle.getModel());
        System.out.println("Kilómetros: " + vehicle.getKmActual());
        System.out.println("Fecha de registro: " + vehicle.getRegistrationDate());
        Call<Vehicle> updateVehicleCall = api.editVehicleByLicense(vehicle.getLicensePlate(), vehicle);
        updateVehicleCall.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(vehicle);
                } else {
                    listener.onFailure("Error al actualizar el vehículo: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                listener.onFailure("Error al conectar con el servidor: " + t.getMessage());
            }
        });
    }
}
