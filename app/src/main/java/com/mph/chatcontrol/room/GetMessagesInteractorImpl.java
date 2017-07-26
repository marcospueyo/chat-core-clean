package com.mph.chatcontrol.room;

import android.os.Handler;

import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.room.contract.GetMessagesInteractor;

import java.util.Arrays;
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
        return Arrays.asList(
                Message.create("test text 0", today, false),
                Message.create("test text 1", today, false));
    }
}
