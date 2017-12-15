package com.mph.chatcontrol.login.contract;
/* Created by macmini on 27/07/2017. */

public interface SharedPreferencesRepository {

    interface OnFinishedListener {

        void onFinished();

    }

    boolean isLoggedIn();

    void setLoggedIn();

    boolean isFirstLaunch();

    void setFirstLaunchFinished();

    void setFCMToken(String token);

    void setFCMTokenSent();

    boolean isTokenAlreadySent();

    String getFCMToken();

    String getUserID();

    String getUserName();

    void setRoomOnDisplay(String roomID);

    void deleteRoomOnDisplay(String roomID);

    boolean isRoomOnDisplay(String roomID);

    void setNotificationsEnabled();

    void setNotificationsDisabled();

    boolean areNotificationsEnabled();

    void clearAllData();
}
