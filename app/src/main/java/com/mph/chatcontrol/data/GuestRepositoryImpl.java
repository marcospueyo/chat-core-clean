package com.mph.chatcontrol.data;
/* Created by macmini on 07/08/2017. */

import com.mph.chatcontrol.base.UpdateOperationCallback;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.network.GuestService;
import com.mph.chatcontrol.network.RestGuest;
import com.mph.chatcontrol.network.RestGuestToGuestMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

import static com.google.common.base.Preconditions.checkNotNull;

public class GuestRepositoryImpl implements GuestRepository {

    private static final String TAG = GuestRepositoryImpl.class.getSimpleName();

    @Nonnull
    private SharedPreferencesRepository sharedPreferencesRepository;
    @Nonnull private EntityDataStore<Persistable> dataStore;
    @Nonnull private final GuestService service;
    @Nonnull private final RestGuestToGuestMapper mapper;

    private final boolean mockData = false;
    private final boolean forceSync = true;

    public GuestRepositoryImpl(@Nonnull SharedPreferencesRepository sharedPreferencesRepository,
                               @Nonnull EntityDataStore<Persistable> dataStore,
                               @Nonnull GuestService service,
                               @Nonnull RestGuestToGuestMapper mapper) {
        this.sharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        this.dataStore = checkNotNull(dataStore);
        this.service = checkNotNull(service);
        this.mapper = checkNotNull(mapper);

        if (mockData)
            insertMockData();
    }

    private void insertMockData() {
        int count = dataStore.count(Guest.class).get().value();
        if (count == 0)
            persistMockEntities();
    }

    private void persistMockEntities() {
        dataStore.insert(createGuestList());
    }

    @Override
    public void getGuests(final GuestGuestsCallback callback) {
        // TODO: 27/09/2017 Define strategy
        int count = dataStore.count(Chat.class).get().value();
        if (forceSync || count == 0) {
            service.getGuests(new GuestService.GetGuestsCallback() {
                @Override
                public void onGuestsLoaded(List<RestGuest> guests) {
                    deleteEntities();
                    persistEntities(mapper.map(guests));
                    callback.onGuestsLoaded(getLocalGuests());
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onGuestsNotAvailable();
                }
            });
        }
        else {
            callback.onGuestsLoaded(getLocalGuests());
        }
    }

    @Override
    public void getGuest(String id, GetSingleGuestCallback callback) {
        callback.onSingleGuestLoaded(getLocalGuest(id));
    }

    @Override
    public void updateGuest(Guest guest, UpdateOperationCallback callback) {
        dataStore.update(guest);
    }

    private Guest getLocalGuest(String id) {
        return dataStore.select(Guest.class).where(Guest.ID.eq(id)).get().firstOrNull();
    }

    private List<Guest> getLocalGuests() {
        return dataStore.select(Guest.class).get().toList();
    }

    private List<Guest> createGuestList() {
        Date today = new Date();
        String[] names = new String[] {
                "Alice", "Bob", "Carol", "Chloe", "Dan", "Emily", "Emma", "Eric", "Eva",
                "Frank", "Gary", "Helen", "Jack", "James", "Jane",
                "Kevin", "Laura", "Leon", "Lilly", "Mary", "Maria",
                "Mia", "Nick", "Oliver", "Olivia", "Patrick", "Robert",
                "Stan", "Vivian", "Wesley", "Zoe"};
        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Guest guest = new Guest();
            guest.setId(/*UUID.randomUUID().toString()*/ names[i]);
            guest.setName(names[i % names.length]);
            guest.setEmail("email" + i + "@example.com");
            guest.setPhone("000000000");
            guest.setStartDate(new Date());
            guest.setEndDate(new Date());
            guest.setRelatedRoomID(names[i % names.length]);

            guests.add(guest);
        }
        return guests;
    }

    private void persistEntities(List<Guest> entities) {
        dataStore.insert(entities);
    }

    private void deleteEntities() {
        dataStore.delete(Guest.class).get().value();
    }
}
