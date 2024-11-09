package com.sphy.pfc_app.model.vehicles;

import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.vehicles.SelectionVehicleListContract;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectionVehicleListModel implements SelectionVehicleListContract.Model {

    private Context context;

    public SelectionVehicleListModel(Context context){
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

                if (vehicles != null) {
                    // Filtra los vehículos con `hide` igual a false
                    List<VehicleDTO> visibleVehicles = new ArrayList<>();
                    for (VehicleDTO vehicle : vehicles) {
                        if (!vehicle.isHide()) {
                            visibleVehicles.add(vehicle);
                        }
                    }
                    // Llama al listener con la lista filtrada
                    listener.onLoadVehiclesSuccess(visibleVehicles);
                } else {
                    listener.onLoadVehiclesError("Error: La respuesta está vacía");
                }
            }

            @Override
            public void onFailure(Call<List<VehicleDTO>> call, Throwable t) {
                Log.e("getVehicles", t.getMessage());
                listener.onLoadVehiclesError("Se ha producido un error al conectar con el servidor");
            }
        });
    }

}

