package com.mph.chatcontrol.login;
/* Created by Marcos on 13/07/2017.*/

import android.support.annotation.NonNull;

import com.mph.chatcontrol.login.contract.FirebaseLoginRepository;
import com.mph.chatcontrol.login.contract.LoginInteractor;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginInteractorImpl implements LoginInteractor,
        FirebaseLoginRepository.OnFinishedListener {

    @NonNull private final SharedPreferencesRepository mSharedPreferencesRepository;

    @NonNull private final FirebaseLoginRepository mFirebaseLoginRepository;

    private OnLoginFinishedListener mListener;

    public LoginInteractorImpl(@NonNull SharedPreferencesRepository sharedPreferencesRepository,
                               @NonNull FirebaseLoginRepository firebaseLoginRepository) {
        mSharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        mFirebaseLoginRepository = checkNotNull(firebaseLoginRepository);
    }

    @Override
    public void login(final String email, final String password,
                      final OnLoginFinishedListener listener) {
        mListener = listener;
        mFirebaseLoginRepository.login(email, password, this);
    }

    @Override
    public boolean isLogged() {
        return mSharedPreferencesRepository.isLoggedIn();
    }

    @Override
    public void onLoginSuccess() {
        mSharedPreferencesRepository.setLoggedIn();
        if (mListener != null)
            mListener.onSuccess();
    }

    @Override
    public void onLoginError() {
        if (mListener != null)
            mListener.onError();
    }
}
