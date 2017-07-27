package com.mph.chatcontrol.login;
/* Created by Marcos on 13/07/2017.*/

import android.support.annotation.NonNull;

import com.mph.chatcontrol.login.contract.LoginInteractor;
import com.mph.chatcontrol.login.contract.LoginPresenter;
import com.mph.chatcontrol.login.contract.LoginView;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;


    public LoginPresenterImpl(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void start() {
        if (loginInteractor.isLogged())
            loginView.navigateToHome();
    }

    @Override public void validateCredentials(String email, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginInteractor.login(email, password, this);
    }

    @Override public void onDestroy() {
        loginView = null;
    }

    @Override public void onEmailError() {
        if (loginView != null) {
            loginView.setEmailError();
            loginView.hideProgress();
        }
    }

    @Override public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }

}
