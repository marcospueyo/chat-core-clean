package com.mph.chatcontrol.chatlist;
/* Created by macmini on 17/07/2017. */

import android.os.Handler;

import com.mph.chatcontrol.data.Chat;

import java.util.Arrays;
import java.util.List;

public class FindChatsInteractorImpl implements FindChatsInteractor {

    @Override
    public void findChats(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(createChatList());
                //listener.onDataNotAvailable();
            }
        }, 2000);
    }

    private List<Chat> createChatList() {
        return Arrays.asList(
                Chat.create("title 1", "descr 1"),
                Chat.create("title 2", "descr 2"),
                Chat.create("title 3", "descr 3"),
                Chat.create("title 4", "descr 4"),
                Chat.create("title 5", "descr 5"),
                Chat.create("title 6", "descr 6"),
                Chat.create("title 7", "descr 7"),
                Chat.create("title 8", "descr 8"));
    }
}
