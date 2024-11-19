package com.sphy.pfc_app.presenter.user;

import android.content.Context;
import android.content.Intent;

import com.sphy.pfc_app.DTO.UserDTO;
import com.sphy.pfc_app.MainActivity;
import com.sphy.pfc_app.model.user.UserRegisterModel;
import com.sphy.pfc_app.view.users.UserRegisterView;

public class UserRegisterPresenter {

    private Context context;
    private UserRegisterModel model;
    private UserRegisterView view;

    public UserRegisterPresenter(Context context, UserRegisterView view) {
        this.context = context;
        this.view = view;
        this.model = new UserRegisterModel(context);
    }

    public void registerUser(UserDTO userDTO) {
        view.showLoading(true);
        model.registerUser(userDTO, new UserRegisterModel.OnUserRegisterListener() {
            @Override
            public void onUserRegisterSuccess(String token) {
                view.showLoading(false);
                view.showMessage("Usuario registrado con éxito");

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onUserRegisterError(String errorMessage) {
                view.showLoading(false);
                view.showMessage(errorMessage);
            }
        });
    }
}
