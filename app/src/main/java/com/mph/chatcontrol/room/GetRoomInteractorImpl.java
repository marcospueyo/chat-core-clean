package com.mph.chatcontrol.room;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;

import java.util.Date;

/* Created by macmini on 24/07/2017. */

public class GetRoomInteractorImpl implements GetRoomInteractor {

    @Override
    public void execute(String roomID, OnFinishedListener listener) {
        listener.onRoomLoaded(getChat());
    }

    private Chat getChat() {
        Date today = new Date();
        return Chat.create("Nombre usuario 1", "Alojamiento 1", 0, today, today, today,
                "Lorem ipsum...", true);
    }
}
