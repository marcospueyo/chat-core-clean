package com.mph.chatcontrol.data;

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.requery.sql.EntityDataStore;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Marcos on 02/08/2017.
 */

public class ChatsRepositoryImpl implements ChatsRepository {

    @Nonnull private SharedPreferencesRepository sharedPreferencesRepository;
    @Nonnull private EntityDataStore dataStore;

    public ChatsRepositoryImpl(@Nonnull SharedPreferencesRepository sharedPreferencesRepository,
                               @Nonnull EntityDataStore dataStore) {
        this.sharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        this.dataStore = checkNotNull(dataStore);
    }

    @Override
    public List<Chat> findActiveChats() {
        return createChatList();
    }

    @Override
    public List<Chat> findArchivedChats() {
        return createArchivedChatList();
    }


    private List<Chat> createChatList() {
        Date today = new Date();
        return Arrays.asList(
                Chat.create("Nombre usuario 1", "Alojamiento 1", UUID.randomUUID().toString(), 0, today, today, today, "Lorem ipsum...", true),
                Chat.create("Nombre usuario 2", "Alojamiento 2", UUID.randomUUID().toString(), 1, today, today, today, "Lorem ipsum...", true),
                Chat.create("Nombre usuario 3", "Alojamiento 3", UUID.randomUUID().toString(), 0, today, today, today, "Lorem ipsum...", true),
                Chat.create("Nombre usuario 4", "Alojamiento 4", UUID.randomUUID().toString(), 1, today, today, today, "Lorem ipsum...", true),
                Chat.create("Nombre usuario 5", "Alojamiento 5", UUID.randomUUID().toString(), 0, today, today, today, "Lorem ipsum...", true),
                Chat.create("Nombre usuario 6", "Alojamiento 6", UUID.randomUUID().toString(), 1, today, today, today, "Lorem ipsum...", true),
                Chat.create("Nombre usuario 7", "Alojamiento 7", UUID.randomUUID().toString(), 0, today, today, today, "Lorem ipsum...", true),
                Chat.create("Nombre usuario 8", "Alojamiento 8", UUID.randomUUID().toString(), 1, today, today, today, "Lorem ipsum...", true));
    }

    private List<Chat> createArchivedChatList() {
        Date today = new Date();
        return Arrays.asList(
                Chat.create("Archivado 1", "Alojamiento 1", UUID.randomUUID().toString(), 0, today, today, today, "Lorem ipsum...", false),
                Chat.create("Archivado 2", "Alojamiento 2", UUID.randomUUID().toString(), 1, today, today, today, "Lorem ipsum...", false),
                Chat.create("Archivado 3", "Alojamiento 3", UUID.randomUUID().toString(), 0, today, today, today, "Lorem ipsum...", false),
                Chat.create("Archivado 4", "Alojamiento 4", UUID.randomUUID().toString(), 1, today, today, today, "Lorem ipsum...", false),
                Chat.create("Archivado 5", "Alojamiento 5", UUID.randomUUID().toString(), 0, today, today, today, "Lorem ipsum...", false),
                Chat.create("Archivado 6", "Alojamiento 6", UUID.randomUUID().toString(), 1, today, today, today, "Lorem ipsum...", false),
                Chat.create("Archivado 7", "Alojamiento 7", UUID.randomUUID().toString(), 0, today, today, today, "Lorem ipsum...", false),
                Chat.create("Archivado 8", "Alojamiento 8", UUID.randomUUID().toString(), 1, today, today, today, "Lorem ipsum...", false));
    }
}
