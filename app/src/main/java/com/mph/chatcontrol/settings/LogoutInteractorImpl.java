package com.mph.chatcontrol.settings;

import android.support.annotation.NonNull;

import com.mph.chatcontrol.device.database.Database;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.settings.contract.LogoutInteractor;

/* Created by macmini on 24/07/2017. */

public class LogoutInteractorImpl implements LogoutInteractor {

    @SuppressWarnings("unused")
    private static final String TAG = LogoutInteractorImpl.class.getSimpleName();

    @NonNull
    private final SharedPreferencesRepository mSharedPreferencesRepository;

    @NonNull
    private final FirebaseLogoutService mFirebaseLogoutService;

    @NonNull
    private final Database mDatabase;

    public LogoutInteractorImpl(@NonNull SharedPreferencesRepository sharedPreferencesRepository,
                                @NonNull FirebaseLogoutService firebaseLogoutService,
                                @NonNull Database database) {
        mSharedPreferencesRepository = sharedPreferencesRepository;
        mFirebaseLogoutService = firebaseLogoutService;
        mDatabase = database;
    }

    @Override
    public void logOut(final OnFinishedListener listener) {
        mFirebaseLogoutService.logout(new FirebaseLogoutService.OnFinishedListener() {
            @Override
            public void onLogoutSuccess() {
                mDatabase.clearDataStore();
                mSharedPreferencesRepository.clearAllData();
                listener.onLogoutFinished();
            }

            @Override
            public void onLogoutError() {
                notifyError(listener);
            }
        });

    }

    private void notifyError(final OnFinishedListener listener) {
        listener.onLogoutError();
    }
}
