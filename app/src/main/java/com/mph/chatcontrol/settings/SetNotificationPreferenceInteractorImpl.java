package com.mph.chatcontrol.settings;

import android.support.annotation.NonNull;

import com.mph.chatcontrol.device.notification.Notifications;
import com.mph.chatcontrol.settings.contract.SetNotificationPreferenceInteractor;

/* Created by macmini on 24/07/2017. */

public class SetNotificationPreferenceInteractorImpl implements SetNotificationPreferenceInteractor {

    @NonNull
    private final Notifications mNotifications;

    public SetNotificationPreferenceInteractorImpl(@NonNull Notifications notifications) {
        mNotifications = notifications;
    }

    @Override
    public void execute(boolean enabled, OnFinishedListener listener) {
        if (enabled) {
            mNotifications.enableNotifications();
        }
        else {
            mNotifications.disableNotifications();
        }
        listener.onNotificationPreferenceChangeSuccess();
    }
}
