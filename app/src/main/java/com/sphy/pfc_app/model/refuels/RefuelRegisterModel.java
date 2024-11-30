package com.sphy.pfc_app.model.refuels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sphy.pfc_app.api.RefuelApi;
import com.sphy.pfc_app.api.RefuelApiInterface;
import com.sphy.pfc_app.contract.refuels.RefuelRegisterContract;
import com.sphy.pfc_app.domain.Refuel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefuelRegisterModel implements RefuelRegisterContract.Model {

    private Context context;

    public RefuelRegisterModel(Context context) {
        this.context = context;
    }



    @Override
    public void insertRefuel(long vehicleId, long stationId, Refuel refuel, RefuelRegisterContract.Model.OnRefuelInsertedListener listener) {
        RefuelApiInterface api = RefuelApi.buildInstance(context);
        Call<Refuel> call = api.addRefuel(vehicleId, stationId, refuel);

        call.enqueue(new Callback<Refuel>() {
            @Override
            public void onResponse(Call<Refuel> call, Response<Refuel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Repostaje registrado correctamente", Toast.LENGTH_LONG).show();
                } else {
                    String errorMessage = null;
                    try {
                        errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e("RefuelError", "Error al registrar: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Refuel> call, Throwable t) {

                Toast.makeText(context, "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }
}