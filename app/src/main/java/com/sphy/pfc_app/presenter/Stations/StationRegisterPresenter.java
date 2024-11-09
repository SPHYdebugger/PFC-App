package com.sphy.pfc_app.presenter.Stations;

import com.sphy.pfc_app.contract.stations.StationRegisterContract;
import com.sphy.pfc_app.contract.vehicles.VehicleRegisterContract;
import com.sphy.pfc_app.domain.Station;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.model.stations.StationRegisterModel;
import com.sphy.pfc_app.model.vehicles.VehicleRegisterModel;
import com.sphy.pfc_app.view.stations.RegisterStationView;
import com.sphy.pfc_app.view.vehicles.RegisterVehicleView;

public class StationRegisterPresenter implements StationRegisterContract.Presenter {

    private RegisterStationView view;
    private StationRegisterModel model;

    public StationRegisterPresenter(RegisterStationView view) {
        this.view = view;
        model = new StationRegisterModel(view);
    }

    @Override
    public void insertStation(Station station) {
        model.insertStation(station, new StationRegisterModel.OnStationInsertedListener() {
            @Override
            public void onStationInsertedSuccess() {
                view.showInsertSuccessMessage();
                view.clearFields();
            }

            @Override
            public void onStationInsertedError(String message) {
                view.showInsertErrorMessage(message);
            }
        });
    }
}
