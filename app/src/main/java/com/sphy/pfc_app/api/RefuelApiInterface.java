package com.sphy.pfc_app.api;

import com.sphy.pfc_app.DTO.RefuelDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.domain.Refuel;
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

public interface RefuelApiInterface {



    // Obtener todos los repostajes
    @GET("/refuels")
    Call<List<RefuelDTO>> getAllRefuels();

    // Obtener repostaje por ID, nombre de la estación o matrícula del vehículo
    @GET("/refuels/{refuelIdentifier}")
    Call<List<Refuel>> findRefuelByIdentifier(@Path("refuelIdentifier") String refuelIdentifier);

    // Crear un nuevo repostaje para un vehículo y estación específicos
    @POST("/refuels/{vehicleId}/{stationId}")
    Call<Refuel> addRefuel(
            @Path("vehicleId") long vehicleId,
            @Path("stationId") long stationId,
            @Body Refuel refuel
    );

    // Eliminar repostaje por ID
    @DELETE("/refuels/{id}")
    Call<Void> deleteRefuel(@Path("id") long refuelId);



}



