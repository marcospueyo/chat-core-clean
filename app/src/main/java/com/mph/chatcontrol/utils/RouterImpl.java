package com.mph.chatcontrol.utils;
/* Created by macmini on 12/12/2017. */

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import static com.google.common.base.Preconditions.checkNotNull;

public class RouterImpl implements Router {

    @NonNull
    private final EventBus mEventBus;

    @NonNull
    private final EventFactory mEventFactory;

    public RouterImpl(@NonNull EventBus eventBus, @NonNull EventFactory eventFactory) {
        mEventBus = checkNotNull(eventBus);
        mEventFactory = checkNotNull(eventFactory);
    }

    @Override
    public void showActiveRoomSearch() {
        mEventBus.post(mEventFactory.getSearchRoomsEvent());
    }

    @Override
    public void refreshActiveRooms() {
        mEventBus.post(mEventFactory.getRefreshRoomsEvent());
    }

    @Override
    public void showArchivedRoomSearch() {
        mEventBus.post(mEventFactory.getSearchRoomsEvent());
    }

    @Override
    public void refreshArchivedRooms() {
        mEventBus.post(mEventFactory.getRefreshRoomsEvent());
    }

    @Override
    public void showGuestSearch() {
        mEventBus.post(mEventFactory.getSearchGuestsEvent());
    }

    @Override
    public void refreshGuests() {
        mEventBus.post(mEventFactory.getRefreshGuestsEvent());
    }
}
