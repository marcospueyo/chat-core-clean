package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

import android.util.Log;

import com.mph.chatcontrol.network.ChatcontrolService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessageRestServiceImpl implements MessageRestService {
    private static final String TAG = MessageRestServiceImpl.class.getSimpleName();

    @Nonnull
    private final ChatcontrolService service;

    public MessageRestServiceImpl(ChatcontrolService service) {
        this.service = checkNotNull(service);
    }

    @Override
    public void sendMessage(final RestMessage message, final SendMessageCallback callback) {
        final Call<ResponseBody> call = service.sendMessage(getCallParameterMap(message));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: ");
                callback.onMessageSent(message);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                callback.onMessageSendError();
            }
        });
    }

    private Map<String, String> getCallParameterMap(RestMessage message) {
        Map<String, String> map = new HashMap<>();
        map.put("id", message.getId());
        map.put("text", message.getText());
        map.put("room_id", message.getRoomID());
        map.put("sender_name", message.getSenderName());
        map.put("sender_id", message.getSenderID());
        return map;
    }
}
