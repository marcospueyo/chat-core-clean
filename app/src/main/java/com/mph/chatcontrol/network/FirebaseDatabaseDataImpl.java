package com.mph.chatcontrol.network;
/* Created by macmini on 30/11/2017. */

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;

import static com.google.common.base.Preconditions.checkNotNull;

public class FirebaseDatabaseDataImpl implements FirebaseDatabaseData {

    @NonNull
    private final DatabaseReference mRootReference;

    public FirebaseDatabaseDataImpl(@NonNull DatabaseReference rootReference) {
        mRootReference = checkNotNull(rootReference);
    }

    @Override
    public DatabaseReference getReferenceForRoom(String roomID) {
        return getChatDatabaseReference().child(roomID);
    }

    @Override
    public DatabaseReference getReferenceForRoomMessages(String roomID) {
        return getMessageDatabaseReference().child(roomID);
    }

    @Override
    public DatabaseReference getReferenceForGuest(String guestID) {
        return getGuestDatabaseReference().child(guestID);
    }

    private DatabaseReference getChatDatabaseReference() {
        return mRootReference.child("rooms");
    }

    public DatabaseReference getGuestDatabaseReference() {
        return mRootReference.child("users");
    }

    public DatabaseReference getMessageDatabaseReference() {
        return mRootReference.child("messages");
    }


}
