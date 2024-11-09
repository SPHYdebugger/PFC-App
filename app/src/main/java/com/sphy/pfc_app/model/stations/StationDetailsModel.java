package com.sphy.pfc_app.model.stations;

import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.api.StationApi;
import com.sphy.pfc_app.api.StationApiInterface;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.stations.StationDetailsContract;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationDetailsModel implements StationDetailsContract.Model {

    private Context context;

    public StationDetailsModel(Context context) {
        this.context = context;
    }




    @Override
    public void getStationDetails(long stationId, OnStationDetailsListener listener) {
        StationApiInterface api = StationApi.buildInstance();
        Call<StationDTO> getStationCall = api.getStationDTOById(stationId);
        getStationCall.enqueue(new Callback<StationDTO>() {
            @Override
            public void onResponse(Call<StationDTO> call, Response<StationDTO> response) {
                Log.e("getStation", response.message());
                StationDTO station = response.body();
                listener.onStationDetailsSuccess(station);
            }

            @Override
            public void onFailure(Call<StationDTO> call, Throwable t) {
                Log.e("getStation", t.getMessage());
                listener.onStationDetailsError("Se ha producido un error al conectar con el servidor");
            }
        });
    }

    @Override
    public void getStationDTO(long stationId, OnStationDetailsListener listener) {
        StationApiInterface api = StationApi.buildInstance();
        Call<StationDTO> getStationCall = api.getStationDTOById(stationId);
        getStationCall.enqueue(new Callback<StationDTO>() {
            @Override
            public void onResponse(Call<StationDTO> call, Response<StationDTO> response) {
                Log.e("getStation", response.message());
                StationDTO station = response.body();
                listener.onStationDetailsSuccess(station);
            }

            @Override
            public void onFailure(Call<StationDTO> call, Throwable t) {
                Log.e("getStation", t.getMessage());
                listener.onStationDetailsError("Se ha producido un error al conectar con el servidor");
            }
        });
    }

    @Override
    public void deleteStation(long stationId, OnDeleteListener listener) {
        StationApiInterface api = StationApi.buildInstance();
        Call<Void> deleteStationCall = api.deleteStation(stationId);
        deleteStationCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onDeleteSuccess();
                } else {
                    Log.e("deleteStation", "Error al eliminar la estación: " + response.message());
                    listener.onDeleteError("Error al eliminar la estación");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("deleteStation", "Error al conectar con el servidor: " + t.getMessage());
                listener.onDeleteError("Se ha producido un error al conectar con el servidor");
            }
        });
    }
}
