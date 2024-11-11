package com.sphy.pfc_app.presenter.Refuels;

import androidx.appcompat.app.AppCompatActivity;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.contract.refuels.RefuelDetailsContract;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.model.refuels.RefuelDetailsModel;
import com.sphy.pfc_app.model.vehicles.VehicleDetailsModel;


public class RefuelDetailsPresenter implements RefuelDetailsContract.Presenter {

    private RefuelDetailsContract.View view;
    private RefuelDetailsContract.Model model;

    public RefuelDetailsPresenter(RefuelDetailsContract.View view) {
        this.view = view;
        this.model = new RefuelDetailsModel(((AppCompatActivity) view).getApplicationContext());
    }


    @Override
    public void getRefuel(String id) {
        model.getRefuel(id, new RefuelDetailsModel.OnRefuelDetailsListener() {
            @Override
            public void onRefuelDetailsSuccess(Refuel refuel) {
                view.displayRefuelDetails(refuel);
            }

            @Override
            public void onRefuelDetailsError(String message) {

            }
        });
    }







}
