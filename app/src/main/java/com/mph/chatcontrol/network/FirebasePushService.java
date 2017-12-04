package com.mph.chatcontrol.network;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mph.chatcontrol.ChatcontrolApplication;

/* Created by macmini on 04/12/2017. */

public class FirebasePushService extends FirebaseInstanceIdService {

    @SuppressWarnings("unused")
    private static final String TAG = FirebasePushService.class.getSimpleName();

    private TokenService mTokenService;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        getDependencies();
    }

    private void getDependencies() {
        mTokenService = ((ChatcontrolApplication) getApplication()).getTokenService(getApplicationContext());
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        if (mTokenService != null) {
            mTokenService.saveToken(refreshedToken, new TokenService.TokenRefreshCallback() {
                @Override
                public void onTokenSaved() {
                    Log.d(TAG, "onTokenSaved: success");
                }

                @Override
                public void onSaveError() {
                    Log.d(TAG, "onSaveError: ");
                }
            });
        }
        else {
            Log.d(TAG, "onTokenRefresh: TokenService is null");
        }
    }
}
