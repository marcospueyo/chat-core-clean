package com.mph.chatcontrol.data;
/* Created by Marcos on 04/08/2017.*/

import java.util.List;

public interface MessagesRepository {

    interface GetMessagesCallback {

        void onMessagesLoaded(List<Message> messages);

        void onNextMessage(Message message);

        void onMessagesNotAvailable();
    }

    interface SendMessageCallback {

        void onMessageSent(Message message);

        void onMessageSendError(Message message);
    }

    void getRoomMessages(String roomID, GetMessagesCallback callback);

    void getRoomMessages(Chat room, GetMessagesCallback callback);

    void stopListeningForMessages();

    Message getMessage(String messageID);

    void insertOwnMessage(String roomID, String text, SendMessageCallback callback);

    Message getLastMessage(String roomID);
}
