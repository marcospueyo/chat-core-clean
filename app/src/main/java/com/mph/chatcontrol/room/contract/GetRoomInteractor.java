package com.mph.chatcontrol.room.contract;
/* Created by macmini on 24/07/2017. */

import android.util.Pair;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;

public interface GetRoomInteractor {

    interface OnFinishedListener {
        void onRoomLoaded(Pair<Chat, ChatInfo> bundle);
        void onRoomLoadError();
    }

    void execute(String roomID, OnFinishedListener listener);
}
