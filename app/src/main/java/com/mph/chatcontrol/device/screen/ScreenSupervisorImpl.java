package com.mph.chatcontrol.device.screen;
/* Created by macmini on 11/12/2017. */

import android.support.annotation.NonNull;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import static com.google.common.base.Preconditions.checkNotNull;


public class ScreenSupervisorImpl implements ScreenSupervisor {

    @NonNull
    private final SharedPreferencesRepository mSharedPreferencesRepository;

    public ScreenSupervisorImpl(@NonNull SharedPreferencesRepository sharedPreferencesRepository) {
        mSharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
    }

    @Override
    public void setRoomOnDisplay(String roomID) {
        mSharedPreferencesRepository.setRoomOnDisplay(roomID);
    }

    @Override
    public void deleteRoomOnDisplay(String roomID) {
        mSharedPreferencesRepository.deleteRoomOnDisplay(roomID);
    }

    @Override
    public boolean isRoomOnDisplay(String roomID) {
        return mSharedPreferencesRepository.isRoomOnDisplay(roomID);
    }
}
