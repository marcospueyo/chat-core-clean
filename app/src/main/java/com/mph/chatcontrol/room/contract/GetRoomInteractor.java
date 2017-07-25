package com.mph.chatcontrol.room.contract;
/* Created by macmini on 24/07/2017. */

import com.mph.chatcontrol.data.Chat;

public interface GetRoomInteractor {

    interface OnFinishedListener {
        void onRoomLoaded(Chat chat);
        void onRoomLoadError();
    }

    void execute(String roomID, OnFinishedListener listener);
}
