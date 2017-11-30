package com.mph.chatcontrol.network;
/* Created by macmini on 30/11/2017. */

import com.google.firebase.database.DatabaseReference;

public interface FirebaseDatabaseData {

    DatabaseReference getReferenceForRoom(String roomID);

    DatabaseReference getReferenceForRoomMessages(String roomID);

    DatabaseReference getReferenceForGuest(String guestID);
}


