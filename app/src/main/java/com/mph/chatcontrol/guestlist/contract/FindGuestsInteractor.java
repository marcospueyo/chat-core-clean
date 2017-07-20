package com.mph.chatcontrol.guestlist.contract;
/* Created by macmini on 19/07/2017. */

import com.mph.chatcontrol.data.Guest;

import java.util.List;

public interface FindGuestsInteractor {

    interface OnFinishedListener {
        void onFinished(List<Guest> guests);
        void onDataNotAvailable();
    }

    void findGuests(OnFinishedListener listener);
}
