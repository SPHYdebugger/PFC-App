package com.sphy.pfc_app.model.vehicles;

import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.vehicles.VehicleRegisterContract;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.domain.Vehicle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleRegisterModel implements VehicleRegisterContract.Model {

    private Context context;

    public VehicleRegisterModel(Context context) {
        this.context = context;
    }



    @Override
    public void insertVehicle(Vehicle vehicle, OnVehicleInsertedListener listener) {
        VehicleApiInterface api = VehicleApi.buildInstance();
        Call<Vehicle> addVehicleCall = api.addVehicle(vehicle);
        addVehicleCall.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.isSuccessful()) {
                    listener.onVehicleInsertedSuccess();
                } else {
                    if (response.code() == 409) {
                        String errorMessage = getErrorMessage(response);
                        listener.onVehicleInsertedError(errorMessage);
                    } else {
                        String errorMessage = getErrorMessage(response);
                        listener.onVehicleInsertedError("Error al insertar el vehículo: " + errorMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Log.e("addVehicle", t.getMessage());
                listener.onVehicleInsertedError("No se ha podido conectar con el servidor");
            }
        });
    }

    private String getErrorMessage(Response<Vehicle> response) {
        try {
            String errorBody = response.errorBody().string();

            JSONObject jsonObject = new JSONObject(errorBody);
            if (jsonObject.has("message")) {
                return jsonObject.getString("message");
            } else {
                return errorBody;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return "Error al procesar la respuesta del servidor";
        }
    }
}