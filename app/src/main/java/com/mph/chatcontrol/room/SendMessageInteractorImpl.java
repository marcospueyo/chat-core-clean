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
        messagesRepository.insertOwnMessage(roomID, text, new MessagesRepository.SendMessageCallback() {
            @Override
            public void onMessageSent(Message message) {
                listener.onMessageSent(message);
            }

            @Override
            public void onMessageSendError(Message message) {
                listener.onMessageSendError(message);
            }
        });
    }
}
