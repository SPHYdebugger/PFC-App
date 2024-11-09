package com.sphy.pfc_app.api;

import static com.sphy.pfc_app.api.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StationApi {

    public static StationApiInterface buildInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(StationApiInterface.class);
    }
}

