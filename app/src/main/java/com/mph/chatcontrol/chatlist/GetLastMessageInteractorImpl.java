package com.mph.chatcontrol.chatlist;
/* Created by macmini on 06/10/2017. */

import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.data.MessagesRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetLastMessageInteractorImpl implements GetLastMessageInteractor {

    @NonNull
    private final MessagesRepository messagesRepository;

    public GetLastMessageInteractorImpl(@NonNull MessagesRepository messagesRepository) {
        this.messagesRepository = checkNotNull(messagesRepository);
    }

    @Override
    public void execute(String roomID, OnFinishedListener listener) {
        Message message = messagesRepository.getLastMessage(roomID);
        listener.onLastMessageFetched(message);
    }
}
