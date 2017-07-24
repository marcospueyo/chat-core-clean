package com.mph.chatcontrol.settings.contract;
/* Created by macmini on 24/07/2017. */

public interface SetNotificationPreferenceInteractor {

    interface OnFinishedListener {
        void onNotificationPreferenceChangeSuccess();
        void onNotificationPreferenceChangeError();
    }

    void execute(boolean enabled, OnFinishedListener listener);
}
