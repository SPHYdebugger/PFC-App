package com.sphy.pfc_app.login;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.sphy.pfc_app.api.Constants;

public class UserApiSecurity {
    private static final String BASE_URL = Constants.BASE_URL;
    private static Retrofit retrofit = null;

    public static Retrofit getUser() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
