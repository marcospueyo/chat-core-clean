package com.mph.chatcontrol.network;
/* Created by macmini on 29/09/2017. */

import java.util.List;

public interface GuestService {
    interface GetGuestsCallback {

        void onGuestsLoaded(List<RestGuest> guests);

        void onDataNotAvailable();
    }

    void getGuests(GetGuestsCallback callback);
}
