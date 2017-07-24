package com.mph.chatcontrol.settings;

import com.mph.chatcontrol.settings.contract.GetNotificationPreferenceInteractor;

/* Created by macmini on 24/07/2017. */

public class GetNotificationPreferenceInteractorImpl implements GetNotificationPreferenceInteractor {

    @Override
    public void getNotificationPreferenceState(OnFinishedListener listener) {
        listener.onNotificationPreferenceStateLoaded(true);
    }
}
