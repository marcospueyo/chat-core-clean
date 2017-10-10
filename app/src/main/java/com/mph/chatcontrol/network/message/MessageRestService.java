package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

public interface MessageRestService {

    interface SendMessageCallback {

        void onMessageSent(RestMessage message);

        void onMessageSendError();
    }

    void sendMessage(RestMessage message, SendMessageCallback callback);
}
