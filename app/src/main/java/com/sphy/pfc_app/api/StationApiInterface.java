package com.sphy.pfc_app.api;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.domain.Station;
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

public interface StationApiInterface {
    @GET("stations")
    Call<List<StationDTO>> getStations();

    @GET("stations/{id}")
    Call<Station> getStationById(@Path("id") long id);

    @GET("vehicle/{id}")
    Call<VehicleDTO> getVehicleDTOById(@Path("id") long id);

    @POST("vehicles")
    Call<Vehicle> addVehicle(@Body Vehicle vehicle);

    @DELETE("vehicles/{id}")
    Call<Void> deleteVehicle(@Path("id") long id);

    @PUT("vehicles/{license}")
    Call<Vehicle> editVehicleByLicense(@Path("license") String license, @Body Vehicle vehicle);


    @PUT("vehicles/{vehicleid}")
    Call hideVehicleById(@Path("vehicleId") long vehicleId);

    @GET("vehicles")
    Call<List<Vehicle>> searchVehicleBylicense(
            @Query("license") String searchText
    );


}
