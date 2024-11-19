package com.sphy.pfc_app.model.vehicles;

import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleDetailsModel implements VehicleDetailsContract.Model {

    private Context context;

    public VehicleDetailsModel(Context context) {
        this.context = context;
    }




    @Override
    public void getVehicleDetails(long vehicleId, OnVehicleDetailsListener listener) {
        VehicleApiInterface api = VehicleApi.buildInstance(context);
        Call<VehicleDTO> getVehicleCall = api.getVehicleDTOById(vehicleId);
        getVehicleCall.enqueue(new Callback<VehicleDTO>() {
            @Override
            public void onResponse(Call<VehicleDTO> call, Response<VehicleDTO> response) {
                Log.e("getVehicle", response.message());
                VehicleDTO vehicle = response.body();
                listener.onVehicleDetailsSuccess(vehicle);
            }

            @Override
            public void onFailure(Call<VehicleDTO> call, Throwable t) {
                Log.e("getVehicle", t.getMessage());
                listener.onVehicleDetailsError("Se ha producido un error al conectar con el servidor");
            }
        });
    }

    @Override
    public void getVehicleDTO(long vehicleId, OnVehicleDetailsListener listener) {
        VehicleApiInterface api = VehicleApi.buildInstance(context);
        Call<VehicleDTO> getVehicleCall = api.getVehicleDTOById(vehicleId);
        getVehicleCall.enqueue(new Callback<VehicleDTO>() {
            @Override
            public void onResponse(Call<VehicleDTO> call, Response<VehicleDTO> response) {
                Log.e("getVehicle", response.message());
                VehicleDTO vehicle = response.body();
                listener.onVehicleDetailsSuccess(vehicle);
            }

            @Override
            public void onFailure(Call<VehicleDTO> call, Throwable t) {
                Log.e("getVehicle", t.getMessage());
                listener.onVehicleDetailsError("Se ha producido un error al conectar con el servidor");
            }
        });
    }

    @Override
    public void deleteVehicle(long vehicleId, OnDeleteListener listener) {
        VehicleApiInterface api = VehicleApi.buildInstance(context);
        Call<Void> deleteVehicleCall = api.deleteVehicle(vehicleId);
        deleteVehicleCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onDeleteSuccess();
                } else {
                    Log.e("deleteClient", "Error al eliminar el vehicle: " + response.message());
                    listener.onDeleteError("Error al eliminar el vehicle");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("deleteVehicle", "Error al conectar con el servidor: " + t.getMessage());
                listener.onDeleteError("Se ha producido un error al conectar con el servidor");
            }
        });
    }
}
