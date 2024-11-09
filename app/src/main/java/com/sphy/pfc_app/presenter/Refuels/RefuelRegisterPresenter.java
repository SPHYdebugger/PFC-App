package com.sphy.pfc_app.presenter.Refuels;

import com.sphy.pfc_app.contract.refuels.RefuelRegisterContract;
import com.sphy.pfc_app.contract.vehicles.VehicleRegisterContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.model.refuels.RefuelRegisterModel;
import com.sphy.pfc_app.model.vehicles.VehicleRegisterModel;
import com.sphy.pfc_app.view.refuels.RegisterRefuelView;
import com.sphy.pfc_app.view.vehicles.RegisterVehicleView;

public class RefuelRegisterPresenter implements RefuelRegisterContract.Presenter {

    private RegisterRefuelView view;
    private RefuelRegisterModel model;

    public RefuelRegisterPresenter(RegisterRefuelView view) {
        this.view = view;
        model = new RefuelRegisterModel(view);
    }

    @Override
    public void insertRefuel(long vehicleId, long stationId, Refuel refuel) {
        model.insertRefuel(vehicleId, stationId, refuel, new RefuelRegisterModel.OnRefuelInsertedListener() {
            @Override
            public void onRefuelInsertedSuccess() {
                //view.showInsertSuccessMessage();
                //view.clearFields();
            }

            @Override
            public void onRefuelInsertedError(String message) {
                //view.showInsertErrorMessage();
            }
        });
    }
}
