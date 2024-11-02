package com.sphy.pfc_app.api;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.domain.Vehicle;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VehicleApiInterface {
    @GET("vehicles")
    Call<List<VehicleDTO>> getVehicles();

    @GET("vehicles/{id}")
    Call<Vehicle> getVehicleById(@Path("id") long id);

    @GET("vehicle/{id}")
    Call<VehicleDTO> getVehicleDTOById(@Path("id") long id);

    @POST("vehicles")
    Call<Vehicle> addVehicle(@Body Vehicle vehicle);

    @DELETE("vehicles/{id}")
    Call<Void> deleteVehicle(@Path("id") long id);

    @PUT("vehicles/{vehicleId}")
    Call<Vehicle> editVehicleById(@Path("vehicleId") long vehicleId, @Body Vehicle vehicle);


    @PUT("vehicles/{vehicleid}")
    Call hideVehicleById(@Path("vehicleId") long vehicleId);

    @GET("vehicles")
    Call<List<Vehicle>> searchVehicleBylicense(
            @Query("license") String searchText
    );


}
