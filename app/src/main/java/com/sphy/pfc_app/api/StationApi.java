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

public class StationApi {

    public static StationApiInterface buildInstance(Context context) {
        // Configura el cliente HTTP con el Interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
                        String token = sharedPreferencesManager.getAuthToken();

                        Request originalRequest = chain.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder();

                        // Agrega el token si existe
                        if (token != null && !token.isEmpty()) {
                            requestBuilder.addHeader("Authorization", "Bearer " + token);
                        }

                        Request newRequest = requestBuilder.build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        // Configura Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(StationApiInterface.class);
    }
}


