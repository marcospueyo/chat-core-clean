package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

import com.mph.chatcontrol.data.Message;

import java.util.List;

public interface MessageService {

    interface SendMessageCallback {

        void onMessageSent(RestMessage message);

        void onMessageSendError();
    }

    interface GetMessagesCallback {

        void onMessagesLoaded(List<RestMessage> messages);

        void onNextMessage(RestMessage message);

        void onMessagesNotAvailable();
    }

    void sendMessage(Message message, SendMessageCallback callback);

    void getRoomMessages(String roomID, GetMessagesCallback callback);

    void stopListeningForMessages();
}
