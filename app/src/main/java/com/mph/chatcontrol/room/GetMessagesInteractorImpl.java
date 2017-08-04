package com.mph.chatcontrol.room;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.data.MessagesRepository;
import com.mph.chatcontrol.room.contract.GetMessagesInteractor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/* Created by macmini on 25/07/2017. */

public class GetMessagesInteractorImpl implements GetMessagesInteractor {

    @NonNull private final MessagesRepository messagesRepository;

    public GetMessagesInteractorImpl(@NonNull MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void execute(final String roomID, final OnFinishedListener listener) {
        List<Message> messages = messagesRepository.getRoomMessages(roomID);
        if (messages == null)
            listener.onMessagesLoadError();
        else
            listener.onMessagesLoaded(messages);
    }
}
