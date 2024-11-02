package com.sphy.pfc_app.model.vehicles;

import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.domain.Vehicle;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleListModel implements VehicleListContract.Model {

    private Context context;

    public VehicleListModel(Context context){
        this.context = context;
    }

    @Override
    public void loadAllVehicles(OnLoadVehicleListener listener) {
        VehicleApiInterface api = VehicleApi.buildInstance();
        Call<List<VehicleDTO>> getVehiclesCall = api.getVehicles();
        getVehiclesCall.enqueue(new Callback<List<VehicleDTO>>() {
            @Override
            public void onResponse(Call<List<VehicleDTO>> call, Response<List<VehicleDTO>> response) {
                Log.e("getVehicles", response.message());
                List<VehicleDTO> vehicles = response.body();
                listener.onLoadVehiclesSuccess(vehicles);
            }

            @Override
            public void onFailure(Call<List<VehicleDTO>> call, Throwable t) {
                Log.e("getVehicles", t.getMessage());
                listener.onLoadVehiclesError("Se ha producido un error al conectar con el servidor");
            }
        });
    }
}

