package com.mph.chatcontrol.data;

import java.util.List;

/**
 * Created by Marcos on 02/08/2017.
 */

public interface ChatsRepository {

    List<Chat> findActiveChats();

    List<Chat> findArchivedChats();

    Chat getChat(String id);

    void updateChat(Chat chat);
}
