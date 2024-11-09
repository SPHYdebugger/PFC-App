package com.sphy.pfc_app.presenter.Stations;

import androidx.appcompat.app.AppCompatActivity;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.contract.stations.StationDetailsContract;
import com.sphy.pfc_app.contract.vehicles.VehicleDetailsContract;
import com.sphy.pfc_app.model.stations.StationDetailsModel;
import com.sphy.pfc_app.model.vehicles.VehicleDetailsModel;


public class StationDetailsPresenter implements StationDetailsContract.Presenter {

    private StationDetailsContract.View view;
    private StationDetailsContract.Model model;

    public StationDetailsPresenter(StationDetailsContract.View view) {
        this.view = view;
        this.model = new StationDetailsModel(((AppCompatActivity) view).getApplicationContext());
    }

    @Override
    public void getStation(long id) {
        model.getStationDTO(id, new StationDetailsModel.OnStationDetailsListener() {
            @Override
            public void onStationDetailsSuccess(StationDTO station) {
                view.displayStationDetails(station);
            }

            @Override
            public void onStationDetailsError(String message) {

            }
        });
    }




    @Override
    public void deleteStation(long id) {
        model.deleteStation(id, new StationDetailsModel.OnDeleteListener() {
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
