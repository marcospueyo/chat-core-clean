package com.mph.chatcontrol.data;

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

    void findActiveChats(GetChatsCallback callback);

    void findArchivedChats(GetChatsCallback callback);

    void getChat(String id, GetSingleChatCallback callback);

    void updateChat(Chat chat);
}
