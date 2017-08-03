package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.data.ChatsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class FindChatsInteractorImpl implements FindChatsInteractor {

    @NonNull private final ChatsRepository mChatsRepository;

    public FindChatsInteractorImpl(@NonNull ChatsRepository mChatsRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
    }

    @Override
    public void findActiveChats(final OnFinishedListener listener) {
        listener.onFinished(mChatsRepository.findActiveChats());
    }

    @Override
    public void findArchivedChats(final OnFinishedListener listener) {
        listener.onFinished(mChatsRepository.findArchivedChats());
    }
}
