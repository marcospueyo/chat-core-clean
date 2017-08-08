package com.mph.chatcontrol.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mph.chatcontrol.login.contract.FirebaseLoginRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 08/08/2017. */

public class FirebaseLoginRepositoryImpl implements FirebaseLoginRepository,
        OnCompleteListener<AuthResult> {

    @NonNull private final FirebaseAuth mFirebaseAuth;

    private OnFinishedListener mListener;

    public FirebaseLoginRepositoryImpl(@NonNull FirebaseAuth mFirebaseAuth) {
        this.mFirebaseAuth = checkNotNull(mFirebaseAuth);
    }

    @Override
    public void login(String email, String password, OnFinishedListener listener) {
        mListener = listener;
        mFirebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (mListener != null) {
            if (task.isSuccessful())
                mListener.onLoginSuccess();
            else
                mListener.onLoginError();
        }


    }
}
