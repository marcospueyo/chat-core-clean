package com.mph.chatcontrol.login;

import android.content.SharedPreferences;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 27/07/2017. */

public class SharedPreferencesRepositoryImpl implements SharedPreferencesRepository {

    private static final String IS_LOGGED_IN = "is_logged_in";

    private static final String IS_FIRST_LAUNCH = "is_first_launch";

    private SharedPreferences mSharedPreferences;
    private FirebaseAuthData mFirebaseAuthData;

    public SharedPreferencesRepositoryImpl(@Nonnull SharedPreferences sharedPreferences,
                                           @Nonnull FirebaseAuthData firebaseAuthData) {
        mSharedPreferences = checkNotNull(sharedPreferences);
        mFirebaseAuthData = checkNotNull(firebaseAuthData);
    }

    @Override
    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    @Override
    public void setLoggedIn() {
        mSharedPreferences.edit().putBoolean(IS_LOGGED_IN, true).apply();
    }

    @Override
    public boolean isFirstLaunch() {
        return mSharedPreferences.getBoolean(IS_FIRST_LAUNCH, false);
    }

    @Override
    public void setFirstLaunchFinished() {
        mSharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, true).apply();
    }

    @Override
    public String getUserID() {
        return mFirebaseAuthData.getUserID();
    }

    @Override
    public String getUserName() {
        return mFirebaseAuthData.getUserName();
    }
}
