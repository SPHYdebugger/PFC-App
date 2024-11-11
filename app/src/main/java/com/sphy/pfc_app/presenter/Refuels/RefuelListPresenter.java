package com.sphy.pfc_app.presenter.Refuels;

import android.content.Context;

import com.sphy.pfc_app.contract.refuels.RefuelListContract;
import com.sphy.pfc_app.domain.Refuel;
import com.sphy.pfc_app.model.refuels.RefuelListModel;

import java.util.List;

public class RefuelListPresenter implements RefuelListContract.Presenter, RefuelListContract.Model.OnLoadRefuelListener {

    private RefuelListContract.View view;
    private RefuelListContract.Model model;


    public RefuelListPresenter(RefuelListContract.View view) {
        this.view = view;
        this.model = new RefuelListModel((Context) view);
    }

    @Override
    public void findRefuelByIdentifier(String identifier) {
        if (view != null) {
            model.findRefuelByIdentifier(identifier, this);
        }
    }

    @Override
    public void onLoadRefuelsSuccess(List<Refuel> refuels) {
        if (view != null) {
            view.listRefuels(refuels);
        }
    }

    @Override
    public void onLoadRefuelsError(String message) {
        if (view != null) {
            view.showMessage(message);
        }
    }
}

