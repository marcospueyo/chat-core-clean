package com.mph.chatcontrol.network;
/* Created by macmini on 30/11/2017. */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public interface RoomListenerRepositoryManager {

    boolean roomHasListeners(String roomID);

    void clearRoomListeners(String roomID);

    void clearAllListeners();

    void setListener(String roomID, DatabaseReference reference, ValueEventListener listener);
}
