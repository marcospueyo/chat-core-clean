package com.mph.chatcontrol.network;
/* Created by macmini on 04/12/2017. */

public interface TokenService {

    interface TokenRefreshCallback {

        void onTokenSaved();

        void onSaveError();
    }

    void saveToken(String token, TokenRefreshCallback callback);

}
