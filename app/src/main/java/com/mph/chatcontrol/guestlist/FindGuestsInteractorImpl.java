package com.mph.chatcontrol.guestlist;

import android.os.Handler;

import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.guestlist.contract.FindGuestsInteractor;

import java.util.Arrays;
import java.util.List;

/* Created by macmini on 19/07/2017. */

public class FindGuestsInteractorImpl implements FindGuestsInteractor {

    @Override
    public void findGuests(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(createGuestList());
                //listener.onDataNotAvailable();
            }
        }, 2000);
    }

    private List<Guest> createGuestList() {
        return Arrays.asList(
                Guest.create("Nombre usuario 0", "email0", "000000000"),
                Guest.create("Nombre usuario 1", "email1", "111111111")
        );
    }
}
