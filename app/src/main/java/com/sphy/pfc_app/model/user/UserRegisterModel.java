package com.sphy.pfc_app.model.user;

import android.content.Context;

import com.sphy.pfc_app.DTO.UserDTO;
import com.sphy.pfc_app.api.UserApi;
import com.sphy.pfc_app.api.UserApiInterface;
import com.sphy.pfc_app.domain.EmailRequest;
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
                    sendWelcomeEmail(userDTO.getEmail(), userDTO.getName());
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
    private void sendWelcomeEmail(String email, String name) {
        EmailRequest emailRequest = new EmailRequest(email, name);
        UserApiInterface api = UserApi.buildInstance();
        System.out.println("Inicio de petición de mail");

        api.sendWelcomeEmail(emailRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("Petición de mail exitosa");
                } else {
                    System.out.println("Fallida petición de mail: " + response.code() + " - " + response.message());
                    if (response.errorBody() != null) {
                        System.out.println("Error: " + response.errorBody().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Error de red en la solicitud");
                if (t != null) {
                    t.printStackTrace();
                }
            }
        });
    }


}
