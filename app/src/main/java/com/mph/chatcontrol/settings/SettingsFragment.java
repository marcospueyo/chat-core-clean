package com.mph.chatcontrol.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.mph.chatcontrol.R;
import com.mph.chatcontrol.base.BaseFragment;
import com.mph.chatcontrol.events.LogoutEvent;
import com.mph.chatcontrol.settings.contract.SettingsPresenter;
import com.mph.chatcontrol.settings.contract.SettingsView;
import com.mph.chatcontrol.widget.MPHSwitch;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/* Created by macmini on 24/07/2017. */

public class SettingsFragment extends BaseFragment implements SettingsView,
        CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.switch_notifications) MPHSwitch swNotifications;

    @BindView(R.id.textView_logout) View vLogout;

    private SettingsPresenter mPresenter;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.init(inflater, R.layout.fragment_settings, container);
        initializeSwitch();
        onLogoutClicked();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(SettingsPresenter presenter) {
        mPresenter = presenter;
    }

    private void initializeSwitch() {
        swNotifications.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            mPresenter.onNotificationsEnabled();
        else
            mPresenter.onNotificationsDisabled();
    }

    private void onLogoutClicked() {
        vLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mPresenter.onLogoutClicked();
    }

    @Override
    public void setNotificationsState(boolean enabled) {
        swNotifications.setCheckedSilently(enabled, this);
    }

    @Override
    public void showLogoutError() {
        Snackbar.make(mView, getString(R.string.logout_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showPreferenceChangeSuccess() {
        Snackbar.make(mView, getString(R.string.preference_change_success), Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showPreferenceChangeError() {
        Snackbar.make(mView, getString(R.string.preference_change_error), Snackbar.LENGTH_LONG)
                .show();
    }
}
