package com.mph.chatcontrol.settings;
/* Created by macmini on 15/12/2017. */

public interface FirebaseLogoutService {

    interface OnFinishedListener {
        void onLogoutSuccess();
        void onLogoutError();
    }

    void logout(OnFinishedListener listener);
}
