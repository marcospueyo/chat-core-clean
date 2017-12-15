package com.mph.chatcontrol.login;
/* Created by Marcos on 13/07/2017.*/

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.login.contract.FirebaseLoginService;
import com.mph.chatcontrol.login.contract.LoginInteractor;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.network.TokenService;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginInteractorImpl implements LoginInteractor,
        FirebaseLoginService.OnFinishedListener, TokenService.TokenRefreshCallback {

    @SuppressWarnings("unused")
    private static final String TAG = LoginInteractorImpl.class.getSimpleName();

    @NonNull
    private final SharedPreferencesRepository mSharedPreferencesRepository;

    @NonNull
    private final FirebaseLoginService mFirebaseLoginService;

    @NonNull
    private final TokenService mTokenService;

    private OnLoginFinishedListener mListener;

    public LoginInteractorImpl(@NonNull SharedPreferencesRepository sharedPreferencesRepository,
                               @NonNull FirebaseLoginService firebaseLoginService,
                               @NonNull TokenService tokenService) {
        Log.d(TAG, "LoginInteractorImpl: UserID:" + sharedPreferencesRepository.getUserID());
        mSharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        mFirebaseLoginService = checkNotNull(firebaseLoginService);
        mTokenService = checkNotNull(tokenService);
    }

    @Override
    public void login(final String email, final String password,
                      final OnLoginFinishedListener listener) {
        mListener = listener;
        mFirebaseLoginService.login(email, password, this);
    }

    @Override
    public boolean isLogged() {
        return mSharedPreferencesRepository.isLoggedIn();
    }

    @Override
    public void onLoginSuccess() {
        Log.d(TAG, "onLoginSuccess: fired");
        mSharedPreferencesRepository.setLoggedIn();
        String tokenFCM = mSharedPreferencesRepository.getFCMToken();
        if (tokenFCM != null) {
            mTokenService.saveToken(tokenFCM, this);
        }
        else {
            finishWithSuccess();
        }
    }

    private void finishWithSuccess() {
        Log.d(TAG, "finishWithSuccess: ");
        if (mListener != null) {
            mListener.onSuccess();
        }
        else {
            Log.d(TAG, "finishWithSuccess: Listener is null");
        }

    }

    @Override
    public void onLoginError() {
        if (mListener != null)
            mListener.onError();
    }

    @Override
    public void onTokenSaved() {
        finishWithSuccess();
    }

    @Override
    public void onSaveError() {
// TODO: 04/12/2017 Should notify login success and token send error
        finishWithSuccess();
    }
}
