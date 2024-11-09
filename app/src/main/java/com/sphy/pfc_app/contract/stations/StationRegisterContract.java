package com.sphy.pfc_app.contract.stations;


import com.sphy.pfc_app.domain.Station;


public interface StationRegisterContract {

    interface View {
        void showInsertSuccessMessage();

        void showInsertErrorMessage(String message);

        void clearFields();
    }

    interface Presenter {
        void insertStation(Station station);
    }

    interface Model {
        interface OnStationInsertedListener {
            void onStationInsertedSuccess();

            void onStationInsertedError(String message);
        }

        void insertStation(Station station, OnStationInsertedListener listener);
    }
}
