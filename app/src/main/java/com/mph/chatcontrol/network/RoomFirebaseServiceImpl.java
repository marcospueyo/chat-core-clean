package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomFirebaseServiceImpl implements RoomService {

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
                List<RestRoom> rooms = new ArrayList<>();
                for (DataSnapshot postSnapshot : children) {
                    RestRoom room = postSnapshot.getValue(RestRoom.class);
                    if (room != null)
                        rooms.add(room);
                }
                callback.onRoomsLoaded(rooms);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable();
            }
        });
    }
}
