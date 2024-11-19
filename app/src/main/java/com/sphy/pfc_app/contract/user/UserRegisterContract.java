package com.sphy.pfc_app.contract.user;

import com.sphy.pfc_app.DTO.UserDTO;

public interface UserRegisterContract {

    interface View {
        void showLoading(boolean isLoading);
        void showMessage(String message);
    }

    interface Presenter {
        void registerUser(UserDTO userDTO);
    }

    interface Model {
        void registerUser(UserDTO userDTO, OnUserRegisterListener listener);

        interface OnUserRegisterListener {
            void onUserRegisterSuccess(String token);
            void onUserRegisterError(String errorMessage);
        }
    }
}
