package com.mph.chatcontrol.network;
/* Created by macmini on 27/10/2017. */

import android.util.Log;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomRestServiceImpl implements RoomService {

    private static final String TAG = RoomRestServiceImpl.class.getSimpleName();

    @Nonnull
    private final ChatcontrolService service;

    @Nonnull
    private final SharedPreferencesRepository sharedPreferencesRepository;

    public RoomRestServiceImpl(@Nonnull ChatcontrolService service,
                               SharedPreferencesRepository sharedPreferencesRepository) {
        this.service = service;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
    }

    @Override
    public void getRooms(final GetRoomsCallback callback) {
        Log.d(TAG, "getRooms: userID:" + sharedPreferencesRepository.getUserID());
        Call<List<RestRoom>> rooms = service.getRooms(
                getCallParameterMap(sharedPreferencesRepository.getUserID()));
        rooms.enqueue(new Callback<List<RestRoom>>() {
            @Override
            public void onResponse(Call<List<RestRoom>> call, Response<List<RestRoom>> response) {
                Map<String, RestRoom> map = new HashMap<>();
                try {
                    for (RestRoom restRoom : response.body()) {
                        map.put(restRoom.getId(), restRoom);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


                if (response.isSuccessful()) {
                    callback.onRoomsLoaded(map);
                }
                else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<RestRoom>> call, Throwable t) {
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
