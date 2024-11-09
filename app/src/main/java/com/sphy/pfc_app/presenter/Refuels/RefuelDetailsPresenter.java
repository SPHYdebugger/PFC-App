package com.sphy.pfc_app.presenter.Refuels;

import androidx.appcompat.app.AppCompatActivity;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.model.vehicles.VehicleDetailsModel;


public class RefuelDetailsPresenter implements VehicleDetailsContract.Presenter {

    private VehicleDetailsContract.View view;
    private VehicleDetailsContract.Model model;

    public RefuelDetailsPresenter(VehicleDetailsContract.View view) {
        this.view = view;
        this.model = new VehicleDetailsModel(((AppCompatActivity) view).getApplicationContext());
    }

    @Override
    public void getVehicle(long id) {
        model.getVehicleDTO(id, new VehicleDetailsModel.OnVehicleDetailsListener() {
            @Override
            public void onVehicleDetailsSuccess(VehicleDTO vehicle) {
                view.displayVehicleDetails(vehicle);
            }

            @Override
            public void onVehicleDetailsError(String message) {

            }
        });
    }




    @Override
    public void deleteVehicle(long id) {
        model.deleteVehicle(id, new VehicleDetailsModel.OnDeleteListener() {
            @Override
            public void onDeleteSuccess() {
                view.showDeleteSuccessMessage();
            }

            @Override
            public void onDeleteError(String message) {
                view.showDeleteErrorMessage();
            }
        });
    }
}
