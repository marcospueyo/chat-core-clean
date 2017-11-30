package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomFirebaseServiceImpl implements RoomRealtimeService {

    @SuppressWarnings("unused")
    private static final String TAG = RoomFirebaseServiceImpl.class.getSimpleName();

    @NonNull
    private final FirebaseDatabaseData mFirebaseDatabaseData;

    @NonNull
    private final RoomListenerRepositoryManager mListenerRepositoryManager;

    public RoomFirebaseServiceImpl(@NonNull FirebaseDatabaseData firebaseDatabaseData,
                                   @NonNull RoomListenerRepositoryManager listenerRepositoryManager) {
        mFirebaseDatabaseData = checkNotNull(firebaseDatabaseData);
        mListenerRepositoryManager = checkNotNull(listenerRepositoryManager);
    }

    @Override
    public void observeRooms(final Iterable<String> roomIDs, final RoomObserverCallback callback) {
        for (String roomID : roomIDs) {
            mListenerRepositoryManager.clearRoomListeners(roomID);
            addListenerToRoom(roomID, callback);
        }
    }

    @Override
    public void stopObservingRoom(String roomID) {
        mListenerRepositoryManager.clearRoomListeners(roomID);
    }

    @Override
    public void stop() {
        mListenerRepositoryManager.clearAllListeners();
    }

    private void addListenerToRoom(String roomID, final RoomObserverCallback callback) {
        DatabaseReference reference = mFirebaseDatabaseData.getReferenceForRoom(roomID);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: fired");
                RestRoom room = dataSnapshot.getValue(RestRoom.class);
                if (room != null) {
                    callback.onRoomChanged(room);
                }
                else {
                    callback.onError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        };
        mListenerRepositoryManager.setListener(roomID, reference, listener);
    }
}
