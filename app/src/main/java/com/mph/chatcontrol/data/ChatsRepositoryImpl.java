package com.mph.chatcontrol.data;

import android.support.annotation.NonNull;

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
import io.requery.sql.EntityDataStore;

import static com.google.common.base.Preconditions.checkNotNull;

/* Created by Marcos on 02/08/2017. */

public class ChatsRepositoryImpl implements ChatsRepository {

    private static final String TAG = ChatsRepositoryImpl.class.getSimpleName();

    @Nonnull private SharedPreferencesRepository sharedPreferencesRepository;
    @Nonnull private EntityDataStore<Persistable> dataStore;
    @NonNull private final RoomService service;
    @NonNull private final RestRoomToChatMapper mapper;

    public ChatsRepositoryImpl(@Nonnull SharedPreferencesRepository sharedPreferencesRepository,
                               @Nonnull EntityDataStore<Persistable> dataStore,
                               @NonNull RoomService service,
                               @NonNull RestRoomToChatMapper mapper) {
        this.sharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        this.dataStore = checkNotNull(dataStore);
        this.service = checkNotNull(service);
        this.mapper = checkNotNull(mapper);
        //insertMockData();
    }

//    private void insertMockData() {
//        int count = dataStore.count(Chat.class).get().value();
//        if (count == 0)
//            persistMockEntities();
//    }

    @Override
    public void findActiveChats(Date inputDate, final GetChatsCallback callback) {
        fetchChats(callback, true /* active */, inputDate);
    }

    @Override
    public void findArchivedChats(Date inputDate, GetChatsCallback callback) {
        fetchChats(callback, false /* active */, inputDate);
    }

    private void fetchChats(final GetChatsCallback callback, final boolean active,
                            final Date inputDate) {
        int count = dataStore.count(Chat.class).get().value();
        if (/*count != 0*/ false)
            callback.onChatsLoaded(getLocalChats(active, inputDate));
        else {
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
    }

    @Override
    public void getChat(String id, GetSingleChatCallback callback) {
        callback.onSingleChatLoaded(getLocalChat(id));
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
                .get()
                .toList();
    }

    private List<Chat> getArchivedChats(Date inputDate) {
        return dataStore.select(Chat.class).where(Chat.ACTIVE.eq(false)).get().toList();
    }

//    private void persistMockEntities() {
//        dataStore.insert(mockActiveChatList());
//        dataStore.insert(mockArchivedChatList());
//    }

    private void persistEntities(List<Chat> entities) {
        dataStore.insert(entities);
    }

    private void deleteEntities() {
        dataStore.delete(Chat.class).get().value();
    }

    /*
    private List<Chat> mockActiveChatList() {
        String[] names = new String[] {
                "Alice", "Bob", "Carol", "Chloe", "Dan", "Emily", "Emma", "Eric", "Eva",
                "Frank", "Gary", "Helen", "Jack", "James", "Jane",
                "Kevin", "Laura", "Leon", "Lilly", "Mary", "Maria",
                "Mia", "Nick", "Oliver", "Olivia", "Patrick", "Robert",
                "Stan", "Vivian", "Wesley", "Zoe"};
        Date today = new Date();
        List<Chat> chats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Chat chat = new Chat();
            chat.setId(names[i]);
            chat.setGuestName(names[i]);
            chat.setTitle(names[i]);
            chat.setDescription("Alojamiento " + i);
            chat.setPendingCount(i % 2);
            chat.setStartDate(today);
            chat.setEndDate(today);
            chat.setLastMsgDate(today);
            chat.setLastMsg("Lorem ipsum...");
            chat.setActive(true);

            chats.add(chat);
        }
        return chats;
    }
    */

    /*
    private List<Chat> mockArchivedChatList() {
        String[] names = new String[] {
                "Kevin", "Laura", "Leon", "Lilly", "Mary", "Maria",
                "Mia", "Nick", "Oliver", "Olivia", "Patrick", "Robert",
                "Stan", "Vivian", "Wesley", "Zoe"};
        Date today = new Date();
        List<Chat> chats = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Chat chat = new Chat();
            chat.setId(UUID.randomUUID().toString());
            chat.setGuestName(names[i]);
            chat.setTitle(names[i]);
            chat.setDescription("Alojamiento " + i);
            chat.setPendingCount(i % 2);
            chat.setStartDate(today);
            chat.setEndDate(today);
            chat.setLastMsgDate(today);
            chat.setLastMsg("Lorem ipsum...");
            chat.setActive(false);

            chats.add(chat);
        }
        return chats;
    }
    */
}
