package com.mph.chatcontrol.login.contract;
/* Created by macmini on 08/08/2017. */

public interface FirebaseLoginService {

    interface OnFinishedListener {
        void onLoginSuccess();
        void onLoginError();
    }

    void login(String email, String password, OnFinishedListener listener);
}
