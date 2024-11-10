package com.sphy.pfc_app.presenter.Stations;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.contract.stations.StationListContract;
import com.sphy.pfc_app.contract.vehicles.VehicleListContract;
import com.sphy.pfc_app.model.stations.StationListModel;
import com.sphy.pfc_app.model.vehicles.VehicleListModel;
import com.sphy.pfc_app.view.stations.StationListView;
import com.sphy.pfc_app.view.vehicles.VehicleListView;

import java.util.List;

public class StationListPresenter implements StationListContract.Presenter, StationListContract.Model.OnLoadStationListener{

    private StationListView view;
    private StationListModel model;

    public StationListPresenter(StationListView view){
        this.view = view;
        model= new StationListModel(view);
    }

    @Override
    public void loadAllStations() {

        model.loadAllStations(this);
    }



    @Override
    public void onLoadStationsSuccess(List<StationDTO> stations) {
        view.listStations(stations);
    }

    @Override
    public void onLoadStationsError(String message) {
        view.showMessage(message);
    }
}
