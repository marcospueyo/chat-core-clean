package com.mph.chatcontrol.network.message;
/* Created by macmini on 09/10/2017. */

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mph.chatcontrol.network.FirebaseDatabaseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessageFirebaseServiceImpl implements MessageFirebaseService {

    private static final String TAG = MessageFirebaseServiceImpl.class.getSimpleName();

    @NonNull
    private final FirebaseDatabaseData mFirebaseDatabaseData;

    private Map<DatabaseReference, ChildEventListener> referenceListenersMap;

    public MessageFirebaseServiceImpl(@Nonnull FirebaseDatabaseData firebaseDatabaseData) {
        mFirebaseDatabaseData = checkNotNull(firebaseDatabaseData);
        referenceListenersMap = new HashMap<>();
    }

    @Override
    public void getRoomMessages(final String roomID, final GetMessagesCallback callback) {
        DatabaseReference messagesReference
                = mFirebaseDatabaseData.getReferenceForRoomMessages(roomID);
        ValueEventListener listener = new ValueEventListener() {
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
        };

        Query query = messagesReference.orderByChild("date");
        query.addListenerForSingleValueEvent(listener);
    }

    private void listenForNewMessages(final String roomID, final GetMessagesCallback callback,
                                      final RestMessage lastMessage) {
        DatabaseReference messagesReference
                = mFirebaseDatabaseData.getReferenceForRoomMessages(roomID);
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: ");
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
        };

        Query query = messagesReference.orderByChild("date");
        if (lastMessage != null) {
            Log.d(TAG, "listenForNewMessages: startAtDate " + lastMessage.getDate());
            query = query.startAt(lastMessage.getDate());
        }
        query.addChildEventListener(listener);

        referenceListenersMap.put(messagesReference, listener);
    }

    @Override
    public void stopListeningForMessages() {
        for (Map.Entry<DatabaseReference, ChildEventListener> entry : referenceListenersMap.entrySet()) {
            DatabaseReference reference = entry.getKey();
            ChildEventListener listener = entry.getValue();
            reference.removeEventListener(listener);
        }
        referenceListenersMap.clear();
    }
}
