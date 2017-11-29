package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomFirebaseServiceImpl implements RoomRealtimeService {

    @NonNull private final DatabaseReference databaseReference;

    public RoomFirebaseServiceImpl(@NonNull DatabaseReference databaseReference) {
        this.databaseReference = checkNotNull(databaseReference);
    }

    @Override
    public void getRooms(final GetRoomsCallback callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Map<String, RestRoom> map = new HashMap<>();
                for (DataSnapshot postSnapshot : children) {
                    RestRoom room = postSnapshot.getValue(RestRoom.class);
                    if (room != null)
                       map.put(room.getId(), room);
                }
                callback.onRoomsLoaded(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void observeRooms(Iterable<String> roomIDs, RoomObserverCallback callback) {

    }
}
