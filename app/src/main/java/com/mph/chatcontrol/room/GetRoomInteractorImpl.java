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
    public void execute(String roomID, OnFinishedListener listener) {
        Chat chat = mChatsRepository.getChat(roomID);
        if (chat == null)
            listener.onRoomLoadError();
        else
            listener.onRoomLoaded(chat);
    }
}
