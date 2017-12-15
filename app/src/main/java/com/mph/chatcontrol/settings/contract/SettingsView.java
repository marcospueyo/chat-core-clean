package com.mph.chatcontrol.settings.contract;
/* Created by macmini on 24/07/2017. */

import com.mph.chatcontrol.base.BaseView;

public interface SettingsView extends BaseView<SettingsPresenter> {

    void setNotificationsState(boolean enabled);

    void showLogoutError();

    void showPreferenceChangeSuccess();

    void showPreferenceChangeError();

}
