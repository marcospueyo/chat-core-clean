package com.mph.chatcontrol.guestlist;

import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.data.GuestRepository;
import com.mph.chatcontrol.guestlist.contract.FindGuestsInteractor;

import java.util.List;

/* Created by macmini on 19/07/2017. */

public class FindGuestsInteractorImpl implements FindGuestsInteractor {

    @NonNull private final GuestRepository mGuestRepository;

    public FindGuestsInteractorImpl(@NonNull GuestRepository guestRepository) {
        this.mGuestRepository = guestRepository;
    }

    @Override
    public void findGuests(final OnFinishedListener listener) {
        mGuestRepository.getGuests(new GuestRepository.GetGuestsCallback() {
            @Override
            public void onGuestsLoaded(List<Guest> guests) {
                listener.onFinished(guests);
            }

            @Override
            public void onGuestsNotAvailable() {
                listener.onDataNotAvailable();
            }
        });
    }
}
