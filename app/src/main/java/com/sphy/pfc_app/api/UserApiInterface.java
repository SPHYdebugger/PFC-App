package com.sphy.pfc_app.api;

import com.sphy.pfc_app.DTO.UserDTO;
import com.sphy.pfc_app.login.JwtResponse;
import com.sphy.pfc_app.login.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiInterface {
    @POST("/register")
    Call<JwtResponse> registerUser(@Body UserDTO userDTO);

}

