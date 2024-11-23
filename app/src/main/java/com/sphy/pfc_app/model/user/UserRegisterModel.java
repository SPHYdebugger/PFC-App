package com.sphy.pfc_app.model.user;

import android.content.Context;

import com.sphy.pfc_app.DTO.UserDTO;
import com.sphy.pfc_app.api.UserApi;
import com.sphy.pfc_app.api.UserApiInterface;
import com.sphy.pfc_app.login.JwtResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterModel {

    private Context context;

    public UserRegisterModel(Context context) {
        this.context = context;
    }

    public void registerUser(UserDTO userDTO, final OnUserRegisterListener listener) {
        UserApiInterface api = UserApi.buildInstance();
        Call<JwtResponse> registerCall = api.registerUser(userDTO);

        registerCall.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JwtResponse tokenDTO = response.body();
                    listener.onUserRegisterSuccess(tokenDTO.getToken());
                    System.out.println("el token es..." + tokenDTO.getToken());
                } else {
                    listener.onUserRegisterError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                listener.onUserRegisterError("Error al conectar con el servidor");
            }
        });
    }

    public interface OnUserRegisterListener {
        void onUserRegisterSuccess(String token);
        void onUserRegisterError(String errorMessage);
    }


}
