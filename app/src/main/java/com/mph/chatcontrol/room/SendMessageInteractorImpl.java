package com.mph.chatcontrol.room;

import android.os.Handler;

import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.room.contract.SendMessageInteractor;

import java.util.Date;

/* Created by macmini on 27/07/2017. */

public class SendMessageInteractorImpl implements SendMessageInteractor {

    @Override
    public void execute(final String roomID, final String text, final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Message insertedMessage = insertMessage(roomID, text);
                if (insertedMessage == null)
                    listener.onMessageSendError();
                else
                    listener.onMessageSent(insertedMessage);
            }
        }, 500);
    }

    private Message insertMessage(String roomID, String text) {
        return text != null ? Message.create(text, new Date(), true) : null;
    }
}
