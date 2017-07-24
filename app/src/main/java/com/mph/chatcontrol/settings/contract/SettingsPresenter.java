package com.mph.chatcontrol.settings.contract;
/* Created by macmini on 24/07/2017. */

import com.mph.chatcontrol.base.presenter.BasePresenter;

public interface SettingsPresenter extends BasePresenter {

    void onNotificationsEnabled();

    void onNotificationsDisabled();

    void onLogoutClicked();
}
