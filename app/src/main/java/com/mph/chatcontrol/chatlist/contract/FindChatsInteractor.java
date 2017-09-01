package com.mph.chatcontrol.chatlist.contract;
import com.mph.chatcontrol.data.Chat;

import java.util.Date;
import java.util.List;
/* Created by macmini on 17/07/2017. */

public interface FindChatsInteractor {

    interface OnFinishedListener {
        void onFinished(List<Chat> chats);
        void onDataNotAvailable();
    }

    void findActiveChats(Date inputDate, OnFinishedListener listener);
    void findArchivedChats(Date inputDate, OnFinishedListener listener);
}
