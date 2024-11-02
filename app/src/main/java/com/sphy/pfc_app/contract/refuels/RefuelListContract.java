package com.sphy.pfc_app.contract.refuels;

import com.sphy.pfc_app.DTO.RefuelDTO;
import com.sphy.pfc_app.domain.Refuel;

import java.util.List;

public interface RefuelListContract {

    interface View {

        void listVehicleRefuels(List<Refuel> refuels);

        void showMessage(String message);
    }

    interface Presenter {
        void findRefuelByIdentifier(String identifier);
    }

    interface Model {
        void findRefuelByIdentifier(String identifier, OnLoadRefuelListener listener);

        interface OnLoadRefuelListener {
            void onLoadRefuelsSuccess(List<Refuel> refuels);
            void onLoadRefuelsError(String message);
        }

        void loadAllRefuels(OnLoadRefuelListener listener);
    }
}
