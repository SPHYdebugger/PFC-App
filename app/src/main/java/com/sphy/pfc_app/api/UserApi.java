package com.sphy.pfc_app.api;

import static com.sphy.pfc_app.api.Constants.BASE_URL;

import android.content.Context;

import com.sphy.pfc_app.login.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApi {



        public static UserApiInterface buildInstance() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(UserApiInterface.class);
        }



}
