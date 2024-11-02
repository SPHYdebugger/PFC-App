package com.sphy.pfc_app.presenter.vehicles;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.domain.Vehicle;
import com.sphy.pfc_app.model.vehicles.VehicleListModel;
import com.sphy.pfc_app.view.vehicles.VehicleListView;

import java.util.List;

public class VehicleListPresenter implements VehicleListContract.Presenter, VehicleListContract.Model.OnLoadVehicleListener{

    private VehicleListView view;
    private VehicleListModel model;

    public VehicleListPresenter(VehicleListView view){
        this.view = view;
        model= new VehicleListModel(view);
    }

    @Override
    public void loadAllVehicles() {
        System.out.println("llega hasta aquí");
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
