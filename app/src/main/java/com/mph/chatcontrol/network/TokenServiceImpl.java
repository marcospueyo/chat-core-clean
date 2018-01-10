package com.mph.chatcontrol.network;
/* Created by macmini on 04/12/2017. */

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenServiceImpl implements TokenService {

    @SuppressWarnings("unused")
    private static final String TAG = TokenServiceImpl.class.getSimpleName();

    @NonNull
    private final ChatcontrolService mService;

    @NonNull
    private final SharedPreferencesRepository mSharedPreferencesRepository;

    private interface SendOperationCallback {

        void onSuccess();

        void onError();
    }

    public TokenServiceImpl(@NonNull ChatcontrolService service,
                            @NonNull SharedPreferencesRepository sharedPreferencesRepository) {
        mService = service;
        mSharedPreferencesRepository = sharedPreferencesRepository;
        Log.d(TAG, "TokenServiceImpl: userID: " + mSharedPreferencesRepository.getUserID()
                + "\nIs logged in:" + mSharedPreferencesRepository.isLoggedIn());
    }

    @Override
    public void saveToken(@NonNull final String token, final TokenRefreshCallback callback) {
        boolean mustSendToken = mustSendToken(token);

        if (isNewToken(token)) {
            saveTokenLocally(token);
        }
        if (mustSendToken) {
            sendTokenToServer(token, new SendOperationCallback() {
                @Override
                public void onSuccess() {
                    setTokenSent();
                    notifyFinished(callback);
                }

                @Override
                public void onError() {
                    notifyFinished(callback);
                }
            });
        }
        else {
            notifyFinished(callback);
        }
    }

    private void setTokenSent() {
        mSharedPreferencesRepository.setFCMTokenSent();
    }

    private boolean mustSendToken(String token) {
        return userIsLoggedIn() && (!isTokenAlreadySent() || isNewToken(token));
    }

    private boolean isNewToken(final String token) {
        return !token.equals(mSharedPreferencesRepository.getFCMToken());
    }

    private boolean isTokenAlreadySent() {
        return mSharedPreferencesRepository.isTokenAlreadySent();
    }

    private void saveTokenLocally(final String token) {
        mSharedPreferencesRepository.setFCMToken(token);
    }

    private boolean userIsLoggedIn() {
        return mSharedPreferencesRepository.isLoggedIn();
    }

    private void sendTokenToServer(final String token, final SendOperationCallback callback) {
        Log.d(TAG, "sendTokenToServer: UserID:" + mSharedPreferencesRepository.getUserID());
        Call<ResponseBody> call = mService.sendToken(
                getCallParameterMap(mSharedPreferencesRepository.getUserID(), token));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                }
                else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError();
            }
        });
    }

    private void notifyFinished(final TokenRefreshCallback callback) {
        callback.onTokenSaved();
    }

    private void notifyError(final TokenRefreshCallback callback) {
        callback.onSaveError();
    }

    private Map<String, String> getCallParameterMap(String id, String token) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", token);
        map.put("platform", "android");
        return map;
    }
}
