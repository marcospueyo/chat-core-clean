package com.mph.chatcontrol.room;

import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatsRepository;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;

import java.util.Date;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 24/07/2017. */

public class GetRoomInteractorImpl implements GetRoomInteractor {

    @NonNull
    private final ChatsRepository mChatsRepository;

    public GetRoomInteractorImpl(@NonNull ChatsRepository mChatsRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
    }

    @Override
    public void execute(String roomID, final OnFinishedListener listener) {
        mChatsRepository.getChat(roomID, new ChatsRepository.GetSingleChatCallback() {
            @Override
            public void onSingleChatLoaded(Chat chat) {
                listener.onRoomLoaded(chat);
            }

            @Override
            public void onChatNotAvailable() {
                listener.onRoomLoadError();
            }
        });
    }
}
