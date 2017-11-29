package com.mph.chatcontrol.data;
/* Created by macmini on 29/11/2017. */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatInfoRepositoryImpl implements ChatInfoRepository {

    @SuppressWarnings("unused")
    private static final String TAG = ChatInfoRepositoryImpl.class.getSimpleName();

    @Nonnull
    private EntityDataStore<Persistable> dataStore;

    public ChatInfoRepositoryImpl(@Nonnull EntityDataStore<Persistable> dataStore) {
        this.dataStore = checkNotNull(dataStore);
    }

    @Override
    public void getSingleChatInfo(String roomID, GetSingleChatInfoCallback callback) {
        if (!chatInfoExists(roomID)) {
            ChatInfo chatInfo = initChatInfo(roomID);
            persistEntities(chatInfo);
            callback.onChatInfoLoaded(chatInfo);
        }
        else {
            callback.onChatInfoLoaded(getLocalChatInfo(roomID));
        }
    }

    @Override
    public void getChatInfoMap(Iterable<String> roomIDs, GetChatInfoCallback callback) {
        Map<String, ChatInfo> map = new HashMap<>();
        List<ChatInfo> entitiesToPersist = new ArrayList<>();
        for (String roomID : roomIDs) {
            ChatInfo chatInfo;
            if (!chatInfoExists(roomID)) {
                chatInfo = initChatInfo(roomID);
                entitiesToPersist.add(chatInfo);
            }
            else {
                chatInfo = getLocalChatInfo(roomID);
            }
            map.put(roomID, chatInfo);
        }
        if (entitiesToPersist.size() > 0) {
            persistEntities(entitiesToPersist);
        }
        callback.onChatInfoMapLoaded(map);
    }

    @Override
    public void updateChatInfo(ChatInfo chatInfo) {
        dataStore.update(chatInfo);
    }

    private ChatInfo initChatInfo(String roomID) {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setId(UUID.randomUUID().toString());
        chatInfo.setRoomID(roomID);
        chatInfo.setReadCount(0);
        return chatInfo;
    }

    private void persistEntities(List<ChatInfo> chatInfoList) {
        dataStore.insert(chatInfoList);
    }

    private void persistEntities(ChatInfo chatInfo) {
        dataStore.insert(chatInfo);
    }

    private boolean chatInfoExists(String roomID) {
        int count = dataStore.count(ChatInfo.class).where(ChatInfo.ROOM_ID.eq(roomID)).get().value();
        return count != 0;
    }

    private ChatInfo getLocalChatInfo(String roomID) {
        return dataStore.select(ChatInfo.class)
                .where(ChatInfo.ROOM_ID.eq(roomID)).get().first();
    }
}
