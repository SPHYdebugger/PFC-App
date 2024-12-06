package com.sphy.pfc_app.contract.vehicles;

import com.sphy.pfc_app.domain.Vehicle;

public interface UpdateVehicleContract {

    interface View {
        void showUpdateSuccessMessage();
        void showUpdateErrorMessage();
        void displayVehicleDetails(Vehicle vehicle);
    }

    interface Presenter {
        void fetchVehicleDetails(long vehicleId);
        void updateVehicle(Vehicle vehicle);
    }

    interface Model {
        interface OnFinishedListener {
            void onSuccess(Vehicle vehicle);
            void onFailure(String error);
        }

        void fetchVehicleDetails(long vehicleId, OnFinishedListener listener);
        void updateVehicleDetails(Vehicle vehicle, OnFinishedListener listener);
    }
}
