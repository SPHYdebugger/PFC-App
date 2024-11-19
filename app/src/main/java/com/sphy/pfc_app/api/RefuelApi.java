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

public class RefuelApi {

    public static RefuelApiInterface buildInstance(Context context) {
        // Configura el cliente HTTP con el Interceptor para agregar el token
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(context);
                        String token = sharedPreferencesManager.getAuthToken();  // Obtener el token desde SharedPreferences

                        Request originalRequest = chain.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder();

                        // Si el token existe, lo agregamos a la cabecera Authorization
                        if (token != null && !token.isEmpty()) {
                            requestBuilder.addHeader("Authorization", "Bearer " + token);
                        }

                        Request newRequest = requestBuilder.build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        // Configuración de Retrofit con el cliente HTTP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)  // Añadimos el cliente HTTP con el Interceptor
                .build();

        return retrofit.create(RefuelApiInterface.class);
    }
}


