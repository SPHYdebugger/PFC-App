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
    Call<List<StationDTO>> getStationsByUserId(@Query("id") long id);

    @GET("stations/{id}")
    Call<Station> getStationById(@Path("id") long id);

    @GET("station/{id}")
    Call<StationDTO> getStationDTOById(@Path("id") long id);

    @POST("stations")
    Call<Station> addStation(@Body Station station);

    @DELETE("stations/{id}")
    Call<Void> deleteStation(@Path("id") long id);

    @PUT("stations/{id}")
    Call<Station> editStationById(@Path("id") long id, @Body Station station);


    @PUT("stations/{id}")
    Call<Void> hideStationById(@Path("Id") long id);

    @GET("stations/{vehicleId}")
    Call<List<Station>> searchStationByVehicle(@Path("vehicleId") long id);


}
