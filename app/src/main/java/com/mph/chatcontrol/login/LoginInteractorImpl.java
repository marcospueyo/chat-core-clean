package com.mph.chatcontrol.login;
/* Created by Marcos on 13/07/2017.*/

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mph.chatcontrol.login.contract.LoginInteractor;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginInteractorImpl implements LoginInteractor {

    @NonNull private final SharedPreferencesRepository mSharedPreferencesRepository;

    public LoginInteractorImpl(@NonNull SharedPreferencesRepository sharedPreferencesRepository) {
        mSharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
    }

    @Override
    public void login(final String email, final String password,
                      final OnLoginFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(email)) {
                    listener.onEmailError();
                    error = true;
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    listener.onPasswordError();
                    error = true;
                    return;
                }
                if (!error){
                    mSharedPreferencesRepository.setLoggedIn();
                    listener.onSuccess();
                }
            }
        }, 2000);
    }

    @Override
    public boolean isLogged() {
        return mSharedPreferencesRepository.isLoggedIn();
    }
}
