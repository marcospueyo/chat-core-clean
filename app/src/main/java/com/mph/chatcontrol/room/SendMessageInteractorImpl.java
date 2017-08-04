package com.mph.chatcontrol.room;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.data.MessagesRepository;
import com.mph.chatcontrol.room.contract.SendMessageInteractor;

import java.util.Date;
import java.util.UUID;

/* Created by macmini on 27/07/2017. */

public class SendMessageInteractorImpl implements SendMessageInteractor {

    @NonNull
    private final MessagesRepository messagesRepository;

    public SendMessageInteractorImpl(@NonNull MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void execute(final String roomID, final String text, final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Message insertedMessage = messagesRepository.insertOwnMessage(roomID, text);
                if (insertedMessage == null)
                    listener.onMessageSendError();
                else
                    listener.onMessageSent(insertedMessage);
            }
        }, 500);
    }

    @Override
    public void execute(Chat chat, String text, OnFinishedListener listener) {
        execute(chat.getId(), text, listener);
    }
}
