package com.mph.chatcontrol.login;

/* Created by Marcos on 13/07/2017.*/
public interface LoginPresenter {
    void validateCredentials(String email, String password);

    void onDestroy();
}
