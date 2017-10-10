package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessageFirebaseServiceImpl implements MessageFirebaseService {

    @Nonnull private final DatabaseReference mDatabaseReference;

    public MessageFirebaseServiceImpl(@Nonnull DatabaseReference databaseReference) {
        this.mDatabaseReference = checkNotNull(databaseReference);
    }

    @Override
    public void getRoomMessages(final String roomID, final GetMessagesCallback callback) {
        DatabaseReference messagesReference = getReferenceForRoom(roomID);
        messagesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                List<RestMessage> messages = new ArrayList<>();
                for (DataSnapshot postSnapshot : children) {
                    RestMessage message = postSnapshot.getValue(RestMessage.class);
                    if (message != null) {
                        messages.add(message);
                    }
                }
                callback.onMessagesLoaded(messages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onMessagesNotAvailable();
            }
        });
    }

    private DatabaseReference getReferenceForRoom(final String roomID) {
        return mDatabaseReference.child(roomID);
    }
}
