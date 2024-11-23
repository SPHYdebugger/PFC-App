package com.sphy.pfc_app.api;

import com.sphy.pfc_app.DTO.UserDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.login.JwtResponse;
import com.sphy.pfc_app.login.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiInterface {
    @POST("/register")
    Call<JwtResponse> registerUser(@Body UserDTO userDTO);

    @GET("/userDTO/{id}")
    Call<UserDTO> getUsernameDTOById(@Path("id") String email);

}

