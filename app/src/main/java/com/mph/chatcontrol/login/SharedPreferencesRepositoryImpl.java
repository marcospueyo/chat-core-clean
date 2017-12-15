package com.mph.chatcontrol.login;

import android.content.SharedPreferences;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 27/07/2017. */

public class SharedPreferencesRepositoryImpl implements SharedPreferencesRepository {

    private static final String IS_LOGGED_IN = "is_logged_in";

    private static final String IS_FIRST_LAUNCH = "is_first_launch";

    private static final String FCM_TOKEN = "fcm_token";

    public static final String TOKEN_IS_SENT = "token_sent";

    public static final String ROOM_ON_DISPLAY = "room_on_display";

    public static final String NOTIFICATIONS_PREFERENCE = "notifications_enabled";

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
    public void setFCMToken(String token) {
        mSharedPreferences.edit().putString(FCM_TOKEN, token).apply();
    }

    @Override
    public void setFCMTokenSent() {
        mSharedPreferences.edit().putBoolean(TOKEN_IS_SENT, true).apply();
    }

    @Override
    public boolean isTokenAlreadySent() {
        return mSharedPreferences.getBoolean(TOKEN_IS_SENT, false);
    }

    @Override
    public String getFCMToken() {
        return mSharedPreferences.getString(FCM_TOKEN, null);
    }

    @Override
    public String getUserID() {
        return mFirebaseAuthData.getUserID();
    }

    @Override
    public String getUserName() {
        return mFirebaseAuthData.getUserName();
    }

    @Override
    public void setRoomOnDisplay(String roomID) {
        mSharedPreferences.edit().putString(ROOM_ON_DISPLAY, roomID).apply();
    }

    @Override
    public void deleteRoomOnDisplay(String roomID) {
        mSharedPreferences.edit().remove(ROOM_ON_DISPLAY).apply();
    }

    @Override
    public boolean isRoomOnDisplay(String roomID) {
        return mSharedPreferences.contains(ROOM_ON_DISPLAY)
                && mSharedPreferences.getString(ROOM_ON_DISPLAY, "").equals(roomID);
    }

    @Override
    public void setNotificationsEnabled() {
        setNotificationsState(true);
    }

    @Override
    public void setNotificationsDisabled() {
        setNotificationsState(false);
    }

    private void setNotificationsState(boolean state) {
        mSharedPreferences.edit().putBoolean(NOTIFICATIONS_PREFERENCE, state).apply();
    }


    @Override
    public boolean areNotificationsEnabled() {
        return mSharedPreferences.getBoolean(NOTIFICATIONS_PREFERENCE, true);
    }

    @Override
    public void clearAllData() {
        mSharedPreferences.edit().clear().apply();
    }
}
