package com.sphy.pfc_app.presenter.vehicles;

import android.content.Context;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.model.vehicles.VehicleListModel;
import com.sphy.pfc_app.view.vehicles.VehicleListView;

import java.util.List;


public class VehicleListPresenter implements VehicleListContract.Presenter, VehicleListContract.Model.OnLoadVehicleListener {

    private VehicleListContract.View view;
    private VehicleListContract.Model model;

    public VehicleListPresenter(VehicleListContract.View view) {
        this.view = view;
        model = new VehicleListModel((Context) view);
    }

    @Override
    public void loadAllVehicles() {
        model.loadAllVehicles(this);
    }

    @Override
    public void onLoadVehiclesSuccess(List<VehicleDTO> vehicles) {
        view.listVehicles(vehicles);
    }

    @Override
    public void onLoadVehiclesError(String message) {
        view.showMessage(message);
    }
}
