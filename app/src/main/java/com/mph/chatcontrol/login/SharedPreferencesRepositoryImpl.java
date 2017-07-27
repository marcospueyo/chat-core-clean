package com.mph.chatcontrol.login;

import android.content.SharedPreferences;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

/* Created by macmini on 27/07/2017. */

public class SharedPreferencesRepositoryImpl implements SharedPreferencesRepository {

    public static final String IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences mSharedPreferences;

    public SharedPreferencesRepositoryImpl(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    @Override
    public void setLoggedIn() {
        mSharedPreferences.edit().putBoolean(IS_LOGGED_IN, true).apply();
    }
}
