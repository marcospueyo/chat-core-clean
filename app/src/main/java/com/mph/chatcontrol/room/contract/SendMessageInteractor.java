package com.mph.chatcontrol.room.contract;
/* Created by macmini on 27/07/2017. */

import com.mph.chatcontrol.data.Message;

public interface SendMessageInteractor {

    interface OnFinishedListener {
        void onMessageSent(Message message);
        void onMessageSendError();
    }

    void execute(String roomID, String text, OnFinishedListener listener);
}
