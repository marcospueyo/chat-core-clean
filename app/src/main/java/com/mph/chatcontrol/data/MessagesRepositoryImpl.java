package com.mph.chatcontrol.data;
/* Created by Marcos on 04/08/2017.*/

import android.support.annotation.NonNull;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

public class MessagesRepositoryImpl implements MessagesRepository {

    public static final String TAG = MessagesRepositoryImpl.class.getSimpleName();

    @NonNull
    private SharedPreferencesRepository sharedPreferencesRepository;

    @NonNull
    private EntityDataStore<Persistable> dataStore;

    public MessagesRepositoryImpl(@NonNull SharedPreferencesRepository sharedPreferencesRepository,
                                  @NonNull EntityDataStore<Persistable> dataStore) {
        this.sharedPreferencesRepository = sharedPreferencesRepository;
        this.dataStore = dataStore;
    }

    @Override
    public List<Message> getRoomMessages(String roomID) {
        return dataStore.select(Message.class).where(Message.ROOM_ID.eq(roomID)).get().toList();
    }

    @Override
    public List<Message> getRoomMessages(Chat room) {
        insertMockData(room);
        return getRoomMessages(room.getId());
    }

    private void insertMockData(Chat room) {
        int count = dataStore.count(Message.class).where(Message.ROOM_ID.eq(room.getId())).get().value();
        if (count == 0)
            persistMockEntities(room);
    }

    private void persistMockEntities(Chat room) {
        dataStore.insert(getMockMessageList(room));
    }

    @Override
    public Message getMessage(String messageID) {
        return dataStore.select(Message.class).where(Message.ID.eq(messageID)).get().firstOrNull();
    }

    @Override
    public Message insertOwnMessage(String roomID, String text) {
        return dataStore.insert(createMessage(roomID, text));
    }

    @Override
    public Message getLastMessage(String roomID) {
        List<Message> messages = dataStore
                .select(Message.class)
                .where(Message.ROOM_ID.eq(roomID))
                .orderBy(Message.DATE.desc())
                .get()
                .toList();
        return (messages.size() > 0) ? messages.get(0) : null;

    }

    // TODO: 04/08/2017 Should have own user 'senderName' field info
    private Message createMessage(String roomID, String text) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setText(text);
        message.setDate(new Date());
        message.setOwnMessage(true);
        message.setRoomId(roomID);
        message.setSenderName("Usuario");
        return message;
    }


    private List<Message> getMockMessageList(Chat room) {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setText("test text " + i);
            message.setDate(new Date());
            message.setOwnMessage(false);
            message.setRoomId(room.getId());
            message.setSenderName(room.getGuestName());
            messages.add(message);
        }
        return messages;
    }

}
