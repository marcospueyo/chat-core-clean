package com.mph.chatcontrol.data;
/* Created by macmini on 07/08/2017. */

import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;

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

    public GuestRepositoryImpl(@Nonnull SharedPreferencesRepository sharedPreferencesRepository,
                               @Nonnull EntityDataStore<Persistable> dataStore) {
        this.sharedPreferencesRepository = checkNotNull(sharedPreferencesRepository);
        this.dataStore = checkNotNull(dataStore);
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
    public List<Guest> getGuests() {
        return dataStore.select(Guest.class).get().toList();
    }

    @Override
    public Guest getGuest(String id) {
        return dataStore.select(Guest.class).where(Guest.ID.eq(id)).get().firstOrNull();
    }

    @Override
    public void updateGuest(Guest guest) {
        dataStore.update(guest);
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
}
