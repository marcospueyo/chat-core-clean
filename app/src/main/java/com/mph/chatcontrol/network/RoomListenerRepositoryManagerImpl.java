package com.mph.chatcontrol.network;
/* Created by macmini on 30/11/2017. */

import android.util.Pair;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RoomListenerRepositoryManagerImpl implements RoomListenerRepositoryManager {

    private Map<String, Pair<DatabaseReference, List<ValueEventListener>>> mRoomListenerMap;

    public RoomListenerRepositoryManagerImpl() {
        mRoomListenerMap = new HashMap<>();
    }

    @Override
    public boolean roomHasListeners(String roomID) {
        return mRoomListenerMap.containsKey(roomID)
                && mRoomListenerMap.get(roomID) != null
                && mRoomListenerMap.get(roomID).second != null
                && mRoomListenerMap.get(roomID).second.size() > 0;
    }

    @Override
    public void clearRoomListeners(String roomID) {
        if (roomHasListeners(roomID)) {
            removeListeners(roomID);
            removeFromCollection(roomID);
        }
    }

    @Override
    public void clearAllListeners() {
        for (Iterator<Map.Entry<String, Pair<DatabaseReference, List<ValueEventListener>>>> it
             = mRoomListenerMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Pair<DatabaseReference, List<ValueEventListener>>> entry = it.next();
            String roomID = entry.getKey();
            removeListeners(roomID);
            removeFromIterator(it);
        }
    }

    private void removeListeners(String roomID) {
        DatabaseReference reference = getRoomDatabaseReference(roomID);
        List<ValueEventListener> listenerList = getRoomListeners(roomID);
        for (ValueEventListener listener : listenerList) {
            reference.removeEventListener(listener);
        }
    }

    private void removeFromCollection(String roomID) {
        mRoomListenerMap.remove(roomID);
    }

    private void removeFromIterator(Iterator<Map.Entry<String,
            Pair<DatabaseReference, List<ValueEventListener>>>> iterator) {
        iterator.remove();
    }

    @Override
    public void setListener(String roomID, DatabaseReference reference,
                            ValueEventListener listener) {
        reference.addValueEventListener(listener);
        if (roomHasListeners(roomID)) {
            getRoomListeners(roomID).add(listener);
        }
        else {
            List<ValueEventListener> listenerList = new ArrayList<>(Collections.singleton(listener));
            Pair<DatabaseReference, List<ValueEventListener>> pair
                    = new Pair<>(reference, listenerList);
            setValue(roomID, pair);
        }
    }

    private DatabaseReference getRoomDatabaseReference(String roomID) {
        return mRoomListenerMap.get(roomID).first;
    }

    private List<ValueEventListener> getRoomListeners(String roomID) {
        // PRE: Room map contains a non-null non-empty list of listeners
        return mRoomListenerMap.get(roomID).second;
    }

    private void setValue(String roomID, Pair<DatabaseReference, List<ValueEventListener>> value) {
        mRoomListenerMap.put(roomID, value);
    }
}
