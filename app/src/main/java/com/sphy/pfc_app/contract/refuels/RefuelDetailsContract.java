package com.sphy.pfc_app.contract.refuels;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.domain.Refuel;

import java.util.List;

public interface RefuelDetailsContract {
    interface View {
        void displayRefuelDetails(Refuel refuel);
        void displayRefuelGrafDetails(List<Refuel> refuel);

    }

    interface Presenter {
        void getRefuel(String refuelId);

    }

    interface Model {
        interface OnRefuelDetailsListener {
            void onRefuelDetailsSuccess(Refuel refuel);
            void onRefuelDetailsError(String message);
        }

        interface OnUpdateListener {
            void onUpdateSuccess();
            void onUpdateError(String message);
        }

        interface OnRefuelDetailsGrafListener {
            void onRefuelDetailsGrafSuccess(Refuel refuel);
            void onRefuelDetailsGrafError(String message);
        }



        void getRefuel(String refuelId, OnRefuelDetailsListener listener);


    }
}