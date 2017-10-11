package com.mph.chatcontrol.room;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.data.MessagesRepository;
import com.mph.chatcontrol.room.contract.GetMessagesInteractor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 25/07/2017. */

public class GetMessagesInteractorImpl implements GetMessagesInteractor {

    @NonNull private final MessagesRepository messagesRepository;

    public GetMessagesInteractorImpl(@NonNull MessagesRepository messagesRepository) {
        this.messagesRepository = checkNotNull(messagesRepository);
    }

    @Override
    public void execute(final String roomID, final OnFinishedListener listener) {
        messagesRepository.getRoomMessages(roomID, new MessagesRepository.GetMessagesCallback() {
            @Override
            public void onMessagesLoaded(List<Message> messages) {
                listener.onMessagesLoaded(messages);
            }

            @Override
            public void onNextMessage(Message message) {
                listener.onNextMessage(message);
            }

            @Override
            public void onMessagesNotAvailable() {
                listener.onMessagesLoadError();
            }
        });
    }

    @Override
    public void execute(final Chat room, final OnFinishedListener listener) {
        execute(room.getId(), listener);
    }
}
