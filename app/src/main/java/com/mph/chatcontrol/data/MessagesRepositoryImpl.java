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
        insertMockData(roomID);
        return dataStore.select(Message.class).where(Message.ROOM_ID.eq(roomID)).get().toList();
    }

    private void insertMockData(String roomID) {
        int count = dataStore.count(Message.class).where(Message.ROOM_ID.eq(roomID)).get().value();
        if (count == 0)
            persistMockEntities(roomID);
    }

    private void persistMockEntities(String roomID) {
        dataStore.insert(getMessageList(roomID));
    }

    @Override
    public Message getMessage(String messageID) {
        return dataStore.select(Message.class).where(Message.ID.eq(messageID)).get().firstOrNull();
    }

    @Override
    public Message insertOwnMessage(String roomID, String text) {
        return dataStore.insert(createMessage(roomID, text));
    }

    private Message createMessage(String roomID, String text) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setText(text);
        message.setDate(new Date());
        message.setOwnMessage(true);
        message.setRoomId(roomID);

        return message;
    }


    private List<Message> getMessageList(String roomID) {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setText("test text " + i);
            message.setDate(new Date());
            message.setOwnMessage(false);
            message.setRoomId(roomID);
            messages.add(message);
        }
        return messages;
    }

}
