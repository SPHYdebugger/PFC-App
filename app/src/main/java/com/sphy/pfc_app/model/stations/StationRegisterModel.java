package com.sphy.pfc_app.model.stations;

import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.api.StationApi;
import com.sphy.pfc_app.api.StationApiInterface;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.stations.StationRegisterContract;
import com.sphy.pfc_app.contract.vehicles.VehicleRegisterContract;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.domain.Vehicle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationRegisterModel implements StationRegisterContract.Model {

    private Context context;

    public StationRegisterModel(Context context) {
        this.context = context;
    }



    @Override
    public void insertStation(Station station, OnStationInsertedListener listener) {
        StationApiInterface api = StationApi.buildInstance();
        Call<Station> addStationCall = api.addStation(station);
        addStationCall.enqueue(new Callback<Station>() {

            @Override
            public void onResponse(Call<Station> call, Response<Station> response) {
                if (response.isSuccessful()) {
                    listener.onStationInsertedSuccess();
                } else {
                    if (response.code() == 409) {
                        String errorMessage = getErrorMessage(response);
                        listener.onStationInsertedError(errorMessage);
                    } else {
                        String errorMessage = getErrorMessage(response);
                        listener.onStationInsertedError("Error al insertar la estación: " + errorMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<Station> call, Throwable t) {

                Log.e("addStation", t.getMessage());
                listener.onStationInsertedError("No se ha podido conectar con el servidor");
            }
        });
    }

        private String getErrorMessage(Response<Station> response) {
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