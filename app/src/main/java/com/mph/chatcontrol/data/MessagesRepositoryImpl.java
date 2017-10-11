package com.mph.chatcontrol.data;
/* Created by Marcos on 04/08/2017.*/

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.network.message.MessageService;
import com.mph.chatcontrol.network.message.RestMessage;
import com.mph.chatcontrol.network.message.RestMessageToMessageMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessagesRepositoryImpl implements MessagesRepository {
    // TODO: 09/10/2017 Refactor Repositories to use LocalStore/RemoteStore architecture
    public static final String TAG = MessagesRepositoryImpl.class.getSimpleName();

    @NonNull
    private SharedPreferencesRepository sharedPreferencesRepository;

    @Nonnull MessageService messageService;

    @Nonnull
    RestMessageToMessageMapper mapper;

    @NonNull
    private EntityDataStore<Persistable> dataStore;

    private final boolean mockData = false;
    private final boolean forceSync = true;


    public MessagesRepositoryImpl(@NonNull SharedPreferencesRepository sharedPreferencesRepository,
                                  @NonNull EntityDataStore<Persistable> dataStore,
                                  @Nonnull RestMessageToMessageMapper mapper,
                                  @Nonnull MessageService messageService) {
        this.sharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        this.dataStore = checkNotNull(dataStore);
        this.mapper = checkNotNull(mapper);
        this.messageService = checkNotNull(messageService);
    }

    @Override
    public void getRoomMessages(final String roomID, final GetMessagesCallback callback){
        if (shouldLoadFromRemoteStore(roomID)) {
            messageService.getRoomMessages(roomID, new MessageService.GetMessagesCallback() {
                @Override
                public void onMessagesLoaded(List<RestMessage> messages) {
                    deleteEntities(roomID);
                    persistEntities(mapper.map(messages));
                    callback.onMessagesLoaded(getLocalRoomMessages(roomID));
                }

                @Override
                public void onNextMessage(RestMessage restMessage) {
                    if (!messageExists(restMessage.getId())) {
                        Log.d(TAG, "onNextMessage: It is a new message");
                        Message message = mapper.map(restMessage);
                        persistEntities(message);
                        callback.onNextMessage(message);
                    }
                }

                @Override
                public void onMessagesNotAvailable() {
                    callback.onMessagesNotAvailable();
                }
            });
        }
        else {
            callback.onMessagesLoaded(getLocalRoomMessages(roomID));
        }
    }

    @Override
    public void stopListeningForMessages() {
        messageService.stopListeningForMessages();
    }

    private boolean messageExists(final String messageID) {
        int count = dataStore.count(Message.class).where(Message.ID.eq(messageID)).get().value();
        return count != 0;
    }

    private boolean shouldLoadFromRemoteStore(String roomID) {
        int count = dataStore.count(Message.class).where(Message.ROOM_ID.eq(roomID)).get().value();
        return (forceSync || count == 0);
    }

    @Override
    public void getRoomMessages(final Chat room, final GetMessagesCallback callback) {
        if (mockData) {
            insertMockData(room);
        }
        getRoomMessages(room.getId(), callback);
    }

    private List<Message> getLocalRoomMessages(String roomID) {
        return dataStore
                .select(Message.class)
                .where(Message.ROOM_ID.eq(roomID))
                .orderBy(Message.DATE.asc())
                .get()
                .toList();
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
    public void insertOwnMessage(String roomID, String text, final SendMessageCallback callback) {
        final Message message = createMessage(roomID, text);
        persistEntities(message);
        callback.onMessageSent(message);
        messageService.sendMessage(message, new MessageService.SendMessageCallback() {
            @Override
            public void onMessageSent(RestMessage restMessage) {
            }

            @Override
            public void onMessageSendError() {
                callback.onMessageSendError(message);
            }
        });
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

    private Message createMessage(String roomID, String text) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setText(text);
        message.setDate(new Date());
        message.setSenderId(sharedPreferencesRepository.getUserID());
        message.setRoomId(roomID);
        message.setSenderName(sharedPreferencesRepository.getUserName());
        return message;
    }


    private List<Message> getMockMessageList(Chat room) {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setText("test text " + i);
            message.setDate(new Date());
            message.setSenderId(UUID.randomUUID().toString());
            message.setRoomId(room.getId());
            message.setSenderName(room.getGuestName());
            messages.add(message);
        }
        return messages;
    }

    private void deleteEntities(String roomID) {
        dataStore
                .delete(Message.class)
                .where(Message.ROOM_ID.eq(roomID))
                .get()
                .value();
    }

    private void persistEntities(Iterable<Message> messages) {
        for (Message message : messages)
                persistEntities(message);
    }

    private void persistEntities(Message message) {
        if (!messageExists(message.getId())) {
            dataStore.insert(message);
        }
    }

}
