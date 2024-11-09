package com.sphy.pfc_app.presenter.vehicles;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.contract.vehicles.SelectionVehicleListContract;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.model.vehicles.SelectionVehicleListModel;
import com.sphy.pfc_app.model.vehicles.VehicleListModel;
import com.sphy.pfc_app.view.vehicles.SelectionVehicleListView;
import com.sphy.pfc_app.view.vehicles.VehicleListView;

import java.util.List;

public class SelectionVehicleListPresenter implements SelectionVehicleListContract.Presenter, SelectionVehicleListContract.Model.OnLoadVehicleListener{

    private SelectionVehicleListView view;
    private SelectionVehicleListModel model;

    public SelectionVehicleListPresenter(SelectionVehicleListView view){
        this.view = view;
        model= new SelectionVehicleListModel(view);
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
