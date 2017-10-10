package com.mph.chatcontrol.login;
/* Created by macmini on 09/10/2017. */

import com.google.firebase.auth.FirebaseAuth;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class FirebaseAuthDataImpl implements FirebaseAuthData {

    private final FirebaseAuth mFirebaseAuth;

    public FirebaseAuthDataImpl(@Nonnull FirebaseAuth firebaseAuth) {
        this.mFirebaseAuth = checkNotNull(firebaseAuth);
    }

    @Override
    public String getUserID() {
        String userID = null;
        if (mFirebaseAuth.getCurrentUser() != null) {
            userID = mFirebaseAuth.getCurrentUser().getUid();
        }
        return userID;
    }

    @Override
    public String getUserName() {
        String userName = null;
        if (mFirebaseAuth.getCurrentUser() != null) {
            userName = mFirebaseAuth.getCurrentUser().getDisplayName();
        }
        return userName;
    }
}
