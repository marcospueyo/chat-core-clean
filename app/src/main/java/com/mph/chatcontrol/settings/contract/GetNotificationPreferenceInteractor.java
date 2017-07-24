package com.mph.chatcontrol.settings.contract;
/* Created by macmini on 24/07/2017. */

public interface GetNotificationPreferenceInteractor {

    interface OnFinishedListener {
        void onNotificationPreferenceStateLoaded(boolean enabled);
    }

    void getNotificationPreferenceState(OnFinishedListener listener);
}
