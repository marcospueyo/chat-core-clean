package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mph.chatcontrol.utils.CCUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessageFirebaseServiceImpl implements MessageFirebaseService {

    private static final String TAG = MessageFirebaseServiceImpl.class.getSimpleName();

    @Nonnull private final DatabaseReference mDatabaseReference;

    public MessageFirebaseServiceImpl(@Nonnull DatabaseReference databaseReference) {
        this.mDatabaseReference = checkNotNull(databaseReference);
    }

    @Override
    public void getRoomMessages(final String roomID, final GetMessagesCallback callback) {
        DatabaseReference messagesReference = getReferenceForRoom(roomID);
        Query query = messagesReference.orderByChild("date");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
                RestMessage lastMessage = null;
                if (messages.size() > 0)
                    lastMessage = messages.get(messages.size() - 1);
                listenForNewMessages(roomID, callback, lastMessage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onMessagesNotAvailable();
            }
        });
    }

    private void listenForNewMessages(final String roomID, final GetMessagesCallback callback,
                                      final RestMessage lastMessage) {
        DatabaseReference messagesReference = getReferenceForRoom(roomID);
        Query query = messagesReference.orderByChild("date");
        if (lastMessage != null) {
            Log.d(TAG, "listenForNewMessages: startAtDate " + lastMessage.getDate());
            query = query.startAt(lastMessage.getDate());
        }
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RestMessage message = dataSnapshot.getValue(RestMessage.class);
                if (message != null) {
                    Log.d(TAG, "onChildAdded: date=" + message.getDate());
                    callback.onNextMessage(message);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private DatabaseReference getReferenceForRoom(final String roomID) {
        return mDatabaseReference.child(roomID);
    }
}
