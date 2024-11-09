package com.sphy.pfc_app.contract.vehicles;


import com.sphy.pfc_app.domain.Vehicle;

public interface VehicleRegisterContract {

    interface View {
        void showInsertSuccessMessage();

        void showInsertErrorMessage();

        void clearFields();
    }

    interface Presenter {
        void insertVehicle(Vehicle vehicle);
    }

    interface Model {
        interface OnVehicleInsertedListener {
            void onVehicleInsertedSuccess();

            void onVehicleInsertedError(String message);
        }

        void insertVehicle(Vehicle vehicle, OnVehicleInsertedListener listener);
    }
}
