package com.mph.chatcontrol.data;
/* Created by macmini on 07/08/2017. */

import com.mph.chatcontrol.base.UpdateOperationCallback;

import java.util.List;

public interface GuestRepository {
    interface GuestGuestsCallback {

        void onGuestsLoaded(List<Guest> guests);

        void onGuestsNotAvailable();
    }

    interface GetSingleGuestCallback {

        void onSingleGuestLoaded(Guest guest);

        void onGuestNotAvailable();
    }

    void getGuests(GuestGuestsCallback callback);

    void getGuest(String id, GetSingleGuestCallback callback);

    void updateGuest(Guest guest, UpdateOperationCallback callback);
}
