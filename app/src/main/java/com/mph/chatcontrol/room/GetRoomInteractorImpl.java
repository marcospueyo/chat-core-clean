package com.mph.chatcontrol.room;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;
import com.mph.chatcontrol.data.ChatInfoRepository;
import com.mph.chatcontrol.data.ChatsRepository;
import com.mph.chatcontrol.room.contract.GetRoomInteractor;

import java.util.Date;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by macmini on 24/07/2017. */

public class GetRoomInteractorImpl implements GetRoomInteractor {

    @NonNull
    private final ChatsRepository mChatsRepository;

    @NonNull
    private final ChatInfoRepository mChatInfoRepository;

    public GetRoomInteractorImpl(@NonNull ChatsRepository mChatsRepository,
                                 @NonNull ChatInfoRepository chatInfoRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
        mChatInfoRepository = checkNotNull(chatInfoRepository);
    }

    @Override
    public void execute(String roomID, final OnFinishedListener listener) {
        mChatsRepository.getChat(roomID, new ChatsRepository.GetSingleChatCallback() {
            @Override
            public void onSingleChatLoaded(Chat chat) {
                getSingleChatInfoRecord(chat, listener);
            }

            @Override
            public void onChatNotAvailable() {
                handleError(listener);
            }
        });
    }

    private void getSingleChatInfoRecord(final Chat chat, final OnFinishedListener listener) {
        mChatInfoRepository.getSingleChatInfo(chat.getId(),
                new ChatInfoRepository.GetSingleChatInfoCallback() {
            @Override
            public void onChatInfoLoaded(ChatInfo chatInfo) {
                listener.onRoomLoaded(new Pair<>(chat, chatInfo));
            }

            @Override
            public void onChatInfoLoadError() {
                handleError(listener);
            }
        });
    }

    private void handleError(final OnFinishedListener listener) {
        listener.onRoomLoadError();
    }
}
