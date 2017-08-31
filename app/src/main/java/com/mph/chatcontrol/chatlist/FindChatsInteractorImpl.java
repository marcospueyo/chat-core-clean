package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatsRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FindChatsInteractorImpl implements FindChatsInteractor {

    @NonNull private final ChatsRepository mChatsRepository;

    public FindChatsInteractorImpl(@NonNull ChatsRepository mChatsRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
    }

    @Override
    public void findActiveChats(final OnFinishedListener listener) {
        mChatsRepository.findActiveChats(new ChatsRepository.GetChatsCallback() {
            @Override
            public void onChatsLoaded(List<Chat> chats) {
                listener.onFinished(chats);
            }

            @Override
            public void onChatsNotAvailable() {
                listener.onDataNotAvailable();
            }
        });
    }

    @Override
    public void findArchivedChats(final OnFinishedListener listener) {
        mChatsRepository.findArchivedChats(new ChatsRepository.GetChatsCallback() {
            @Override
            public void onChatsLoaded(List<Chat> chats) {
                listener.onFinished(chats);
            }

            @Override
            public void onChatsNotAvailable() {
                listener.onDataNotAvailable();
            }
        });
    }
}
