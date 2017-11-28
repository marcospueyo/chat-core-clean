package com.mph.chatcontrol.network;
/* Created by macmini on 20/11/2017. */

import android.util.Log;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestServiceCloudImpl implements GuestService {

    private static final String TAG = GuestServiceCloudImpl.class.getSimpleName();

    private final ChatcontrolService mService;

    @Nonnull
    private final SharedPreferencesRepository sharedPreferencesRepository;

    public GuestServiceCloudImpl(ChatcontrolService service,
                                 @Nonnull SharedPreferencesRepository sharedPreferencesRepository) {
        mService = service;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
    }

    @Override
    public void getGuests(final GetGuestsCallback callback) {
        Log.d(TAG, "getGuests: userID:" + sharedPreferencesRepository.getUserID());
        Call<List<RestGuest>>  call = mService.getGuests(
                getCallParameterMap(sharedPreferencesRepository.getUserID()));
        call.enqueue(new Callback<List<RestGuest>>() {
            @Override
            public void onResponse(Call<List<RestGuest>> call, Response<List<RestGuest>> response) {
                if (response.isSuccessful()) {
                    callback.onGuestsLoaded(response.body());
                }
                else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<RestGuest>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    private Map<String, String> getCallParameterMap(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("owner_id", id);
        return map;
    }
}
