package com.mph.chatcontrol.network;
/* Created by macmini on 20/11/2017. */

import android.support.annotation.NonNull;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class GuestServiceCloudImpl implements GuestService {

    @SuppressWarnings("unused")
    private static final String TAG = GuestServiceCloudImpl.class.getSimpleName();

    @NonNull
    private final ChatcontrolService mService;

    @NonNull
    private final SharedPreferencesRepository mSharedPreferencesRepository;

    public GuestServiceCloudImpl(@NonNull ChatcontrolService service,
                                 @NonNull SharedPreferencesRepository sharedPreferencesRepository) {
        mService = checkNotNull(service);
        mSharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
    }

    @Override
    public void getGuests(final GetGuestsCallback callback) {
        Call<List<RestGuest>>  call = mService.getGuests(
                getCallParameterMap(mSharedPreferencesRepository.getUserID()));
        call.enqueue(new Callback<List<RestGuest>>() {
            @Override
            public void onResponse(@NonNull Call<List<RestGuest>> call,
                                   @NonNull Response<List<RestGuest>> response) {
                if (response.isSuccessful()) {
                    callback.onGuestsLoaded(response.body());
                }
                else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RestGuest>> call, @NonNull Throwable t) {
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
