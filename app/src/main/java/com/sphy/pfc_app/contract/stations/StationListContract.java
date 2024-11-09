package com.sphy.pfc_app.contract.stations;

import com.sphy.pfc_app.DTO.StationDTO;
import com.sphy.pfc_app.DTO.VehicleDTO;

import java.util.List;

public interface StationListContract {

    interface View{
        void listStations(List<StationDTO> stations);
        void showMessage(String message);
    }
    interface Presenter{
        void loadAllStations();
    }
    interface Model{
        interface OnLoadStationListener{
            void onLoadStationsSuccess(List<StationDTO> stations);
            void onLoadStationsError(String message);
        }
        void loadAllStations(OnLoadStationListener listener);
    }

}