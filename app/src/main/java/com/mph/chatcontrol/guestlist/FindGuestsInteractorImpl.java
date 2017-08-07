package com.mph.chatcontrol.guestlist;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.data.GuestRepository;
import com.mph.chatcontrol.guestlist.contract.FindGuestsInteractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/* Created by macmini on 19/07/2017. */

public class FindGuestsInteractorImpl implements FindGuestsInteractor {

    @NonNull private final GuestRepository mGuestRepository;

    public FindGuestsInteractorImpl(@NonNull GuestRepository guestRepository) {
        this.mGuestRepository = guestRepository;
    }

    @Override
    public void findGuests(final OnFinishedListener listener) {
        listener.onFinished(mGuestRepository.getGuests());
    }
}
