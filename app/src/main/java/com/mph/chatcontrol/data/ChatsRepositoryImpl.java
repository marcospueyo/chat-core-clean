package com.mph.chatcontrol.data;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Marcos on 02/08/2017.
 */

public class ChatsRepositoryImpl implements ChatsRepository {

    private static final String TAG = ChatsRepositoryImpl.class.getSimpleName();

    @Nonnull private SharedPreferencesRepository sharedPreferencesRepository;
    @Nonnull private EntityDataStore<Persistable> dataStore;

    public ChatsRepositoryImpl(@Nonnull SharedPreferencesRepository sharedPreferencesRepository,
                               @Nonnull EntityDataStore<Persistable> dataStore) {
        this.sharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        this.dataStore = checkNotNull(dataStore);
        insertMockData();
    }

    private void insertMockData() {
        int count = dataStore.count(Chat.class).get().value();
        if (count == 0)
            persistMockEntities();
    }

    @Override
    public List<Chat> findActiveChats() {
        return getActiveChats();
    }

    @Override
    public List<Chat> findArchivedChats() {
        return getArchivedChats();
    }

    @Override
    public Chat getChat(String id) {
        return dataStore.select(Chat.class).where(Chat.ID.eq(id)).get().firstOrNull();
    }

    @Override
    public void updateChat(Chat chat) {
        dataStore.update(chat);
    }

    private List<Chat> getActiveChats() {
        return dataStore.select(Chat.class).where(Chat.ACTIVE.eq(true)).get().toList();
    }

    private List<Chat> getArchivedChats() {
        return dataStore.select(Chat.class).where(Chat.ACTIVE.eq(false)).get().toList();
    }

    private void persistMockEntities() {
        dataStore.insert(mockActiveChatList());
        dataStore.insert(mockArchivedChatList());
    }

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
            chat.setId(UUID.randomUUID().toString());
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
}
