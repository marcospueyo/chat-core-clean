package com.mph.chatcontrol.chatlist;
/* Created by macmini on 06/10/2017. */

import com.mph.chatcontrol.data.Message;

public interface GetLastMessageInteractor {

    interface OnFinishedListener {
        void onLastMessageFetched(Message message);
        void onLastActivityLoadError();
    }

    void execute(String roomID, OnFinishedListener listener);
}
