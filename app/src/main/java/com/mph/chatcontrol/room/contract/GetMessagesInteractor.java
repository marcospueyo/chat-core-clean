package com.mph.chatcontrol.room.contract;
/* Created by macmini on 25/07/2017. */

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Message;

import java.util.List;

public interface GetMessagesInteractor {

    interface OnFinishedListener {

        void onMessagesLoaded(List<Message> messages);

        void onNextMessage(Message message);

        void onMessagesLoadError();
    }

    void execute(String roomID, OnFinishedListener listener);

    void execute(Chat room, OnFinishedListener listener);

    void stop();
}
