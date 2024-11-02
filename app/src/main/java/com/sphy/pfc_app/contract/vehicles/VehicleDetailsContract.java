package com.sphy.pfc_app.contract.vehicles;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.domain.Vehicle;

public interface VehicleDetailsContract {
    interface View {
        void displayVehicleDetails(VehicleDTO vehicle);
        void showUpdateSuccessMessage();
        void showUpdateErrorMessage();
        void showDeleteSuccessMessage();
        void showDeleteErrorMessage();
    }

    interface Presenter {
        void getVehicle(long vehicleId);

        void deleteVehicle(long vehicleId);
    }

    interface Model {
        interface OnVehicleDetailsListener {
            void onVehicleDetailsSuccess(VehicleDTO vehicle);
            void onVehicleDetailsError(String message);
        }

        interface OnUpdateListener {
            void onUpdateSuccess();
            void onUpdateError(String message);
        }

        interface OnDeleteListener {
            void onDeleteSuccess();
            void onDeleteError(String message);
        }

        void getVehicleDetails(long vehicleId, OnVehicleDetailsListener listener);

        void getVehicleDTO(long vehicleId, OnVehicleDetailsListener listener);

        void deleteVehicle(long vehicleId, OnDeleteListener listener);
    }
}