package com.mph.chatcontrol.room;

import android.os.Handler;

import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.room.contract.GetMessagesInteractor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* Created by macmini on 25/07/2017. */

public class GetMessagesInteractorImpl implements GetMessagesInteractor {

    @Override
    public void execute(final String roomID, final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onMessagesLoaded(getMessageList(roomID));
            }
        }, 1000);
    }

    private List<Message> getMessageList(String roomID) {
        Date today = new Date();
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            messages.add(Message.create("test text " + i, today, i % 2 == 0));
        }
        return messages;
    }
}
