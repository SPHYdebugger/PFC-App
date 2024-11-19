package com.sphy.pfc_app.model.refuels;

import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.api.RefuelApi;
import com.sphy.pfc_app.api.RefuelApiInterface;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.refuels.RefuelDetailsContract;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.domain.Refuel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefuelDetailsModel implements RefuelDetailsContract.Model {

    private Context context;

    public RefuelDetailsModel(Context context) {
        this.context = context;
    }

    @Override
    public void getRefuel(String refuelId, RefuelDetailsContract.Model.OnRefuelDetailsListener listener) {
        RefuelApiInterface api = RefuelApi.buildInstance(context);
        Call<List<Refuel>> getRefuelCall = api.findRefuelByIdentifier(refuelId);

        getRefuelCall.enqueue(new Callback<List<Refuel>>() {
            @Override
            public void onResponse(Call<List<Refuel>> call, Response<List<Refuel>> response) {
                Log.e("getRefuel", response.message());
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Refuel refuel = response.body().get(0);
                    System.out.println("Detalles de .... " + refuel.getId());
                    listener.onRefuelDetailsSuccess(refuel);
                } else {
                    listener.onRefuelDetailsError("No se encontraron detalles para este refuel.");
                }
            }

            @Override
            public void onFailure(Call<List<Refuel>> call, Throwable t) {
                Log.e("getRefuel", t.getMessage());
                listener.onRefuelDetailsError("Se ha producido un error al conectar con el servidor");
            }
        });
    }


}
