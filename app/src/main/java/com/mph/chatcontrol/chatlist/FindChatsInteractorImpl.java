package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.os.Handler;

import com.mph.chatcontrol.chatlist.contract.FindChatsInteractor;
import com.mph.chatcontrol.data.Chat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FindChatsInteractorImpl implements FindChatsInteractor {

    // TODO: 17/07/2017 Implement ChatsDataSource
    //@NonNull
    //private final ChatsDataSource mChatsRepository;

    @Override
    public void findActiveChats(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(createChatList());
                //listener.onDataNotAvailable();
            }
        }, 2000);
    }

    @Override
    public void findArchivedChats(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(createArchivedChatList());
                //listener.onDataNotAvailable();
            }
        }, 2000);
    }

    private List<Chat> createChatList() {
        Date today = new Date();
        return Arrays.asList(
                Chat.create("Nombre usuario 1", "Alojamiento 1", 0, today, today),
                Chat.create("Nombre usuario 2", "Alojamiento 2", 1, today, today),
                Chat.create("Nombre usuario 3", "Alojamiento 3", 0, today, today),
                Chat.create("Nombre usuario 4", "Alojamiento 4", 1, today, today),
                Chat.create("Nombre usuario 5", "Alojamiento 5", 0, today, today),
                Chat.create("Nombre usuario 6", "Alojamiento 6", 1, today, today),
                Chat.create("Nombre usuario 7", "Alojamiento 7", 0, today, today),
                Chat.create("Nombre usuario 8", "Alojamiento 8", 1, today, today));
    }

    private List<Chat> createArchivedChatList() {
        Date today = new Date();
        return Arrays.asList(
                Chat.create("Archivado 1", "Alojamiento 1", 0, today, today),
                Chat.create("Archivado 2", "Alojamiento 2", 1, today, today),
                Chat.create("Archivado 3", "Alojamiento 3", 0, today, today),
                Chat.create("Archivado 4", "Alojamiento 4", 1, today, today),
                Chat.create("Archivado 5", "Alojamiento 5", 0, today, today),
                Chat.create("Archivado 6", "Alojamiento 6", 1, today, today),
                Chat.create("Archivado 7", "Alojamiento 7", 0, today, today),
                Chat.create("Archivado 8", "Alojamiento 8", 1, today, today));
    }
}
