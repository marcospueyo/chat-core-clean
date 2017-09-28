package com.mph.chatcontrol.data;

import java.util.Date;
import java.util.List;

/**
 * Created by Marcos on 02/08/2017.
 */

public interface ChatsRepository {
    interface GetChatsCallback {
        void onChatsLoaded(List<Chat> chats);

        void onChatsNotAvailable();
    }

    interface GetSingleChatCallback {
        void onSingleChatLoaded(Chat chat);

        void onChatNotAvailable();
    }

    void findActiveChats(Date inputDate, GetChatsCallback callback);

    void findActiveChatsSorted(Date inputDate, GetChatsCallback callback);

    void findArchivedChats(Date inputDate, GetChatsCallback callback);

    void findArchivedChatsSorted(Date inputDate, GetChatsCallback callback);

    void getChat(String id, GetSingleChatCallback callback);

    void updateChat(Chat chat);
}
