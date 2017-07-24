package com.mph.chatcontrol.settings;

import com.mph.chatcontrol.settings.contract.SetNotificationPreferenceInteractor;

/* Created by macmini on 24/07/2017. */

public class SetNotificationPreferenceInteractorImpl implements SetNotificationPreferenceInteractor {

    @Override
    public void execute(boolean enabled, OnFinishedListener listener) {
        listener.onNotificationPreferenceChangeSuccess();
    }
}
