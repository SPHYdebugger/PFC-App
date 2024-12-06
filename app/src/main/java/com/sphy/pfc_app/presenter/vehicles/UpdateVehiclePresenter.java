package com.sphy.pfc_app.presenter.vehicles;

import androidx.appcompat.app.AppCompatActivity;

import com.sphy.pfc_app.contract.vehicles.UpdateVehicleContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.model.vehicles.UpdateVehicleModel;
import com.sphy.pfc_app.model.vehicles.VehicleDetailsModel;

public class UpdateVehiclePresenter implements UpdateVehicleContract.Presenter {

    private UpdateVehicleContract.View view;
    private UpdateVehicleContract.Model model;

    public UpdateVehiclePresenter(UpdateVehicleContract.View view) {
        this.view = view;
        this.model = new UpdateVehicleModel(((AppCompatActivity) view).getApplicationContext());
    }

    @Override
    public void fetchVehicleDetails(long vehicleId) {
        model.fetchVehicleDetails(vehicleId, new UpdateVehicleContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(Vehicle vehicle) {
                view.displayVehicleDetails(vehicle);
            }

            @Override
            public void onFailure(String error) {
                view.showUpdateErrorMessage();
            }
        });
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        model.updateVehicleDetails(vehicle, new UpdateVehicleContract.Model.OnFinishedListener() {
            @Override
            public void onSuccess(Vehicle updatedVehicle) {
                view.showUpdateSuccessMessage();
            }

            @Override
            public void onFailure(String error) {
                view.showUpdateErrorMessage();
            }
        });
    }
}
