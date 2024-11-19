package com.sphy.pfc_app.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {
    @Headers({"Content-Type: application/json"})
    @POST("/login")
    Call<JwtResponse> login(@Body LoginRequest loginRequest);
}