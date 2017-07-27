package com.mph.chatcontrol.login.contract;
/* Created by macmini on 27/07/2017. */

public interface SharedPreferencesRepository {

    interface OnFinishedListener {
        void onFinished();
    }
    boolean isLoggedIn();
    void setLoggedIn();
}
