package com.mph.chatcontrol.room;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;

import java.util.Date;
import java.util.UUID;

/* Created by macmini on 24/07/2017. */

public class GetRoomInteractorImpl implements GetRoomInteractor {

    @Override
    public void execute(String roomID, OnFinishedListener listener) {
        listener.onRoomLoaded(getChat(roomID));
    }

    private Chat getChat(String roomID) {
        Date today = new Date();
        return Chat.create("Nombre usuario 1", "Alojamiento 1", UUID.randomUUID().toString(), 0, today, today, today,
                "Lorem ipsum...", roomID.hashCode() % 2 == 0);
    }
}
