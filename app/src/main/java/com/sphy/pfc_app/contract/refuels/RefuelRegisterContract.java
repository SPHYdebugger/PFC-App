package com.sphy.pfc_app.contract.refuels;


import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.domain.Vehicle;

public interface RefuelRegisterContract {

    interface View {
        void showInsertSuccessMessage();

        void showInsertErrorMessage();

        void clearFields();
    }

    interface Presenter {
        void insertRefuel(long vehicleId, long stationId, Refuel refuel);
    }

    interface Model {
        interface OnRefuelInsertedListener {
            void onRefuelInsertedSuccess();

            void onRefuelInsertedError(String message);
        }

        void insertRefuel(long vehicleId, long stationId, Refuel refuel, OnRefuelInsertedListener listener);
    }
}
