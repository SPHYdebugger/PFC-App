package com.sphy.pfc_app.model.refuels;


import android.content.Context;
import android.util.Log;

import com.sphy.pfc_app.api.RefuelApi;
import com.sphy.pfc_app.api.RefuelApiInterface;
import com.sphy.pfc_app.contract.refuels.RefuelListContract;
import com.sphy.pfc_app.domain.Refuel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefuelListModel implements RefuelListContract.Model {

    private Context context;

    public RefuelListModel(Context context){
        this.context = context;
    }

    @Override
    public void findRefuelByIdentifier(String identifier, OnLoadRefuelListener listener) {
        RefuelApiInterface api = RefuelApi.buildInstance();
        Call<List<Refuel>> getRefuelsCall = api.findRefuelByIdentifier(identifier);
        getRefuelsCall.enqueue(new Callback<List<Refuel>>() {
            @Override
            public void onResponse(Call<List<Refuel>> call, Response<List<Refuel>> response) {
                Log.e("getRefuels", response.message());
                List<Refuel> refuels = response.body();
                listener.onLoadRefuelsSuccess(refuels);
            }

            @Override
            public void onFailure(Call<List<Refuel>> call, Throwable t) {
                Log.e("getRefuels", t.getMessage());
                listener.onLoadRefuelsError("Se ha producido un error al conectar con el servidor");
            }
        });
    }

    @Override
    public void loadAllRefuels(OnLoadRefuelListener listener) {

    }
}
