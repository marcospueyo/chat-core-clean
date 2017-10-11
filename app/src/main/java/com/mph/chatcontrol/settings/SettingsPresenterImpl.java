package com.mph.chatcontrol.settings;
/* Created by macmini on 24/07/2017. */

import android.support.annotation.NonNull;

import com.mph.chatcontrol.settings.contract.GetNotificationPreferenceInteractor;
import com.mph.chatcontrol.settings.contract.LogoutInteractor;
import com.mph.chatcontrol.settings.contract.SetNotificationPreferenceInteractor;
import com.mph.chatcontrol.settings.contract.SettingsPresenter;
import com.mph.chatcontrol.settings.contract.SettingsView;

import static com.google.common.base.Preconditions.checkNotNull;

public class SettingsPresenterImpl implements SettingsPresenter,
        LogoutInteractor.OnFinishedListener, SetNotificationPreferenceInteractor.OnFinishedListener,
        GetNotificationPreferenceInteractor.OnFinishedListener{

    private static final String TAG = SettingsPresenterImpl.class.getSimpleName();

    @NonNull private final SettingsView mSettingsView;

    @NonNull private final LogoutInteractor mLogoutInteractor;

    @NonNull private final SetNotificationPreferenceInteractor mSetNotificationPreferenceInteractor;

    @NonNull private final GetNotificationPreferenceInteractor mGetNotificationPreferenceInteractor;

    public SettingsPresenterImpl(@NonNull SettingsView settingsView,
                                 @NonNull LogoutInteractor logoutInteractor,
                                 @NonNull SetNotificationPreferenceInteractor
                                         setNotificationPreferenceInteractor,
                                 @NonNull GetNotificationPreferenceInteractor
                                         getNotificationPreferenceInteractor) {
        mLogoutInteractor = checkNotNull(logoutInteractor);
        mSetNotificationPreferenceInteractor = checkNotNull(setNotificationPreferenceInteractor);
        mGetNotificationPreferenceInteractor = checkNotNull(getNotificationPreferenceInteractor);
        mSettingsView = checkNotNull(settingsView);
        mSettingsView.setPresenter(this);
    }

    @Override
    public void start() {
        mGetNotificationPreferenceInteractor.getNotificationPreferenceState(this);
    }

    @Override
    public void stop() {

    }

    @Override
    public void onLogoutFinished() {
        mSettingsView.handleLogoutSuccess();
    }

    @Override
    public void onLogoutError() {
        mSettingsView.showLogoutError();
    }

    @Override
    public void onNotificationPreferenceStateLoaded(boolean enabled) {
        mSettingsView.setNotificationsState(enabled);
    }

    @Override
    public void onNotificationPreferenceChangeSuccess() {
        mSettingsView.showPreferenceChangeSuccess();
    }

    @Override
    public void onNotificationPreferenceChangeError() {
        mSettingsView.showPreferenceChangeError();
    }

    @Override
    public void onNotificationsEnabled() {
        mSetNotificationPreferenceInteractor.execute(true /* enabled */ , this);
    }

    @Override
    public void onNotificationsDisabled() {
        mSetNotificationPreferenceInteractor.execute(false /* disabled */ , this);
    }

    @Override
    public void onLogoutClicked() {
        mLogoutInteractor.logOut(this);
    }
}
