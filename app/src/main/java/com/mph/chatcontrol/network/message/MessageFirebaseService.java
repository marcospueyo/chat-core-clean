package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

import java.util.List;

public interface MessageFirebaseService {

    interface GetMessagesCallback {

        void onMessagesLoaded(List<RestMessage> messages);

        void onNextMessage(RestMessage message);

        void onMessagesNotAvailable();
    }

    void getRoomMessages(String roomID, GetMessagesCallback callback);
}
