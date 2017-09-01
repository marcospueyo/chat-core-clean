package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.support.annotation.NonNull;

import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatsRepository;

import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FindChatsInteractorImpl implements FindChatsInteractor {

    @NonNull private final ChatsRepository mChatsRepository;

    public FindChatsInteractorImpl(@NonNull ChatsRepository mChatsRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
    }

    @Override
    public void findActiveChats(Date inputDate, final OnFinishedListener listener) {
        mChatsRepository.findActiveChats(inputDate, new ChatsRepository.GetChatsCallback() {
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
    public void findArchivedChats(Date inputDate, final OnFinishedListener listener) {
        mChatsRepository.findArchivedChats(inputDate, new ChatsRepository.GetChatsCallback() {
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
