package com.mph.chatcontrol.room;

import com.mph.chatcontrol.room.contract.GetRoomInteractor;

/* Created by macmini on 24/07/2017. */

public class GetRoomInteractorImpl implements GetRoomInteractor {

    @Override
    public void execute(String roomID, OnFinishedListener listener) {
        listener.onRoomLoadError();
    }
}
