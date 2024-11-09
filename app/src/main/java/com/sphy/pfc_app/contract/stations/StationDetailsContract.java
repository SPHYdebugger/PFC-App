package com.sphy.pfc_app.contract.stations;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;

public interface StationDetailsContract {
    interface View {
        void displayStationDetails(StationDTO station);
        void showUpdateSuccessMessage();
        void showUpdateErrorMessage();
        void showDeleteSuccessMessage();
        void showDeleteErrorMessage();
    }

    interface Presenter {
        void getStation(long stationId);

        void deleteStation(long stationId);
    }

    interface Model {
        interface OnStationDetailsListener {
            void onStationDetailsSuccess(StationDTO station);
            void onStationDetailsError(String message);
        }

        interface OnUpdateListener {
            void onUpdateSuccess();
            void onUpdateError(String message);
        }

        interface OnDeleteListener {
            void onDeleteSuccess();
            void onDeleteError(String message);
        }

        void getStationDetails(long stationId, OnStationDetailsListener listener);

        void getStationDTO(long vehicleId, OnStationDetailsListener listener);

        void deleteStation(long vehicleId, OnDeleteListener listener);
    }
}