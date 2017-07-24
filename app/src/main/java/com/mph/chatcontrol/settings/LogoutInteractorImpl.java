package com.mph.chatcontrol.settings;

import com.mph.chatcontrol.settings.contract.LogoutInteractor;

/* Created by macmini on 24/07/2017. */

public class LogoutInteractorImpl implements LogoutInteractor {

    @Override
    public void logOut(OnFinishedListener listener) {
        listener.onLogoutError();
    }
}
