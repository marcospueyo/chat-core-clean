package com.mph.chatcontrol.chatlist.contract;
import android.util.Pair;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.ChatInfo;

import java.util.Date;
import java.util.List;
/* Created by macmini on 17/07/2017. */

public interface FindChatsInteractor {

    interface OnFinishedListener {

        void onFinished(List<Pair<Chat, ChatInfo>> chats);

        void onChatChanged(Pair<Chat, ChatInfo> chatInfoPair);

        void onChatChangedError();

        void onDataNotAvailable();

    }

    void findActiveChats(Date inputDate, OnFinishedListener listener);

    void findArchivedChats(Date inputDate, OnFinishedListener listener);

    void stopUpdates();
}
