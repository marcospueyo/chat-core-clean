package com.mph.chatcontrol.login.contract;
/* Created by Marcos on 13/07/2017.*/

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void navigateToHome();
}
