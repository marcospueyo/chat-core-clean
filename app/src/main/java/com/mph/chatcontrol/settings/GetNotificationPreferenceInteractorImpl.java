package com.mph.chatcontrol.settings;

import android.support.annotation.NonNull;

import com.mph.chatcontrol.device.notification.Notifications;
import com.mph.chatcontrol.settings.contract.GetNotificationPreferenceInteractor;

/* Created by macmini on 24/07/2017. */

public class GetNotificationPreferenceInteractorImpl implements GetNotificationPreferenceInteractor {

    @NonNull
    private final Notifications mNotifications;

    public GetNotificationPreferenceInteractorImpl(@NonNull Notifications notifications) {
        mNotifications = notifications;
    }

    @Override
    public void getNotificationPreferenceState(OnFinishedListener listener) {
        listener.onNotificationPreferenceStateLoaded(mNotifications.canShowNotifications());
    }
}
