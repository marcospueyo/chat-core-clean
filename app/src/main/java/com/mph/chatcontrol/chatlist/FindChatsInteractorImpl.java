package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatsRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class FindChatsInteractorImpl implements FindChatsInteractor {

    // TODO: 17/07/2017 Implement ChatsRepository
    @NonNull private final ChatsRepository mChatsRepository;

    public FindChatsInteractorImpl(@NonNull ChatsRepository mChatsRepository) {
        this.mChatsRepository = checkNotNull(mChatsRepository);
    }

    @Override
    public void findActiveChats(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(mChatsRepository.findActiveChats());
            }
        }, 2000);
    }

    @Override
    public void findArchivedChats(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(mChatsRepository.findArchivedChats());
            }
        }, 2000);
    }
}
