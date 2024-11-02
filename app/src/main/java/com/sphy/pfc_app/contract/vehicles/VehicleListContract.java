package com.sphy.pfc_app.contract.vehicles;

import com.sphy.pfc_app.DTO.VehicleDTO;
import com.sphy.pfc_app.domain.Vehicle;

import java.util.List;

public interface VehicleListContract {

    interface View{
        void listVehicles(List<VehicleDTO> vehicles);
        void showMessage(String message);
    }
    interface Presenter{
        void loadAllVehicles();
    }
    interface Model{
        interface OnLoadVehicleListener{
            void onLoadVehiclesSuccess(List<VehicleDTO> vehicles);
            void onLoadVehiclesError(String message);
        }
        void loadAllVehicles(OnLoadVehicleListener listener);
    }

}