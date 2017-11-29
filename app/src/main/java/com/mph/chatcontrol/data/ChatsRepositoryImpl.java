package com.mph.chatcontrol.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.network.RestRoom;
import com.mph.chatcontrol.network.RestRoomToChatMapper;
import com.mph.chatcontrol.network.RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.requery.Persistable;
import io.requery.query.Condition;
import io.requery.sql.EntityDataStore;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by Marcos on 02/08/2017. */

public class ChatsRepositoryImpl implements ChatsRepository {

    private static final String TAG = ChatsRepositoryImpl.class.getSimpleName();

    @Nonnull private SharedPreferencesRepository sharedPreferencesRepository;
    @Nonnull private EntityDataStore<Persistable> dataStore;
    @NonNull private final RoomService service;
    @NonNull private final RestRoomToChatMapper mapper;

    private final boolean forceSync = true;

    public ChatsRepositoryImpl(@Nonnull SharedPreferencesRepository sharedPreferencesRepository,
                               @Nonnull EntityDataStore<Persistable> dataStore,
                               @NonNull RoomService service,
                               @NonNull RestRoomToChatMapper mapper) {
        this.sharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        this.dataStore = checkNotNull(dataStore);
        this.service = checkNotNull(service);
        this.mapper = checkNotNull(mapper);
    }

    @Override
    public void findActiveChatsSorted(Date inputDate, GetChatsCallback callback) {

    }

    @Override
    public void findArchivedChatsSorted(Date inputDate, GetChatsCallback callback) {

    }

    @Override
    public void findActiveChats(Date inputDate, final GetChatsCallback callback) {
        fetchChats(callback, true, inputDate);
    }

    @Override
    public void findArchivedChats(Date inputDate, GetChatsCallback callback) {
        fetchChats(callback, false, inputDate);
    }

    private void fetchChats(final GetChatsCallback callback, final boolean active,
                            final Date inputDate) {
        // TODO: 27/09/2017 Define strategy
        int count = dataStore.count(Chat.class).get().value();
        if (forceSync || count == 0) {
            service.getRooms(new RoomService.GetRoomsCallback() {
                @Override
                public void onRoomsLoaded(List<RestRoom> rooms) {
                    deleteEntities();
                    persistEntities(mapper.map(rooms));
                    callback.onChatsLoaded(getLocalChats(active, inputDate));
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onChatsNotAvailable();
                }
            });
        }
        else {
            callback.onChatsLoaded(getLocalChats(active, inputDate));
        }
    }

    @Override
    public void getChat(String id, GetSingleChatCallback callback) {
        Chat chat = getLocalChat(id);
        callback.onSingleChatLoaded(chat);
    }

    private Chat getLocalChat(String id) {
        return dataStore.select(Chat.class).where(Chat.ID.eq(id)).get().firstOrNull();
    }

    @Override
    public void updateChat(Chat chat) {
        dataStore.update(chat);
    }

    private List<Chat> getLocalChats(boolean active, Date inputDate) {
        return active ? getActiveChats(inputDate) : getArchivedChats(inputDate);
    }

    private List<Chat> getActiveChats(Date inputDate) {
        return dataStore
                .select(Chat.class)
                .where(Chat.START_DATE.lessThanOrEqual(inputDate))
                .and(Chat.END_DATE.greaterThanOrEqual(inputDate))
                .orderBy(Chat.LAST_MSG_DATE.desc())
                .get()
                .toList();
    }

    private List<Chat> getArchivedChats(Date inputDate) {
        return dataStore
                .select(Chat.class)
                .where(Chat.END_DATE.lessThan(inputDate))
                .orderBy(Chat.LAST_MSG_DATE.desc())
                .get()
                .toList();
    }

    private void persistEntities(List<Chat> entities) {
        dataStore.insert(entities);
    }

    private void deleteEntities() {
        dataStore.delete(Chat.class).get().value();
    }
}
