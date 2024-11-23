package com.sphy.pfc_app.model.stations;

import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.api.StationApi;
import com.sphy.pfc_app.api.StationApiInterface;
import com.sphy.pfc_app.api.VehicleApi;
import com.sphy.pfc_app.api.VehicleApiInterface;
import com.sphy.pfc_app.contract.stations.StationListContract;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.login.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationListModel implements StationListContract.Model {

    private Context context;

    public StationListModel(Context context){
        this.context = context;
    }

    @Override
    public void loadAllStations(OnLoadStationListener listener) {
        StationApiInterface api = StationApi.buildInstance(context);

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
        String token = sharedPreferencesManager.getAuthToken();

        System.out.println("el token es.... " + token);

        long id = sharedPreferencesManager.getUserIdFromJWT(token);

        System.out.println("el userId es .... " + id);

        // Llamada al API filtrando por UserId
        Call<List<StationDTO>> getStationsCall = api.getStationsByUserId(id);
        getStationsCall.enqueue(new Callback<List<StationDTO>>() {
            @Override
            public void onResponse(Call<List<StationDTO>> call, Response<List<StationDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("getStations", response.message());
                    List<StationDTO> stations = response.body();

                    // Filtra las estaciones con `hide` igual a false
                    List<StationDTO> visibleStations = new ArrayList<>();
                    for (StationDTO station : stations) {
                        if (!station.isHide()) {
                            visibleStations.add(station);
                        }
                    }
                    // Llama al listener con la lista filtrada
                    listener.onLoadStationsSuccess(visibleStations);
                } else {
                    listener.onLoadStationsError("Error: La respuesta está vacía o no exitosa");
                }
            }

            @Override
            public void onFailure(Call<List<StationDTO>> call, Throwable t) {
                Log.e("getStations", t.getMessage());
                listener.onLoadStationsError("Se ha producido un error al conectar con el servidor");
            }
        });
    }


}

