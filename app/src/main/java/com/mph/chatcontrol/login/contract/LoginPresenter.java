package com.mph.chatcontrol.login.contract;

import com.mph.chatcontrol.base.presenter.BasePresenter;

/* Created by Marcos on 13/07/2017.*/
public interface LoginPresenter extends BasePresenter {
    void validateCredentials(String email, String password);

    void onDestroy();
}
