package com.mph.chatcontrol.settings.contract;
/* Created by macmini on 24/07/2017. */

public interface LogoutInteractor {

    interface OnFinishedListener {
        void onLogoutFinished();
        void onLogoutError();
    }

    void logOut(OnFinishedListener listener);
}
