package com.mph.chatcontrol.room;

import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Message;
import com.mph.chatcontrol.data.MessagesRepository;
import com.mph.chatcontrol.room.contract.GetMessagesInteractor;


import java.util.List;


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
    public void stop() {
        messagesRepository.stopListeningForMessages();
    }
}
