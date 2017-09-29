package com.mph.chatcontrol.network;
/* Created by macmini on 29/09/2017. */

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GuestServiceImpl implements GuestService {

    @NonNull
    private final DatabaseReference databaseReference;

    public GuestServiceImpl(@NonNull DatabaseReference databaseReference) {
        this.databaseReference = checkNotNull(databaseReference);
    }

    @Override
    public void getGuests(final GetGuestsCallback callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                List<RestGuest> guests = new ArrayList<>();
                for (DataSnapshot postSnapshot : children) {
                    RestGuest room = postSnapshot.getValue(RestGuest.class);
                    if (room != null)
                        guests.add(room);
                }
                callback.onGuestsLoaded(guests);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable();
            }
        });
    }
}
