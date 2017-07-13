package com.mph.chatcontrol.login;
/* Created by Marcos on 13/07/2017.*/

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onEmailError();

        void onPasswordError();

        void onSuccess();
    }

    void login(String email, String password, OnLoginFinishedListener listener);

}
