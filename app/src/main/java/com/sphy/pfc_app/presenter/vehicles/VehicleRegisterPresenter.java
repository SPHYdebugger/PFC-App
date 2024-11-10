package com.sphy.pfc_app.presenter.vehicles;

import com.sphy.pfc_app.contract.vehicles.VehicleRegisterContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.model.vehicles.VehicleRegisterModel;
import com.sphy.pfc_app.view.vehicles.RegisterVehicleView;

public class VehicleRegisterPresenter implements VehicleRegisterContract.Presenter {

    private RegisterVehicleView view;
    private VehicleRegisterModel model;

    public VehicleRegisterPresenter(RegisterVehicleView view) {
        this.view = view;
        model = new VehicleRegisterModel(view);
    }

    @Override
    public void insertVehicle(Vehicle vehicle) {
        model.insertVehicle(vehicle, new VehicleRegisterModel.OnVehicleInsertedListener() {
            @Override
            public void onVehicleInsertedSuccess() {
                view.showInsertSuccessMessage();
                view.clearFields();
            }

            @Override
            public void onVehicleInsertedError(String message) {
                view.showInsertErrorMessage(message);
            }
        });
    }
}
