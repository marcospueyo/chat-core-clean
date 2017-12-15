package com.mph.chatcontrol.settings;
/* Created by macmini on 15/12/2017. */

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseLogoutServiceImpl implements FirebaseLogoutService {

    @SuppressWarnings("unused")
    private static final String TAG = FirebaseLogoutServiceImpl.class.getSimpleName();

    @NonNull
    private final FirebaseAuth mFirebaseAuth;

    public FirebaseLogoutServiceImpl(@NonNull FirebaseAuth firebaseAuth) {
        mFirebaseAuth = firebaseAuth;
    }

    @Override
    public void logout(OnFinishedListener listener) {
        mFirebaseAuth.signOut();
        listener.onLogoutSuccess();
    }
}
