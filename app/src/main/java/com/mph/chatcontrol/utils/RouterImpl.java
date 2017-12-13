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
    public void showActiveRoomSearch(String query) {
        mEventBus.post(mEventFactory.getSearchRoomsEvent(query));
    }

    @Override
    public void refreshActiveRooms() {
        mEventBus.post(mEventFactory.getRefreshRoomsEvent());
    }

    @Override
    public void showArchivedRoomSearch(String query) {
        mEventBus.post(mEventFactory.getSearchRoomsEvent(query));
    }

    @Override
    public void refreshArchivedRooms() {
        mEventBus.post(mEventFactory.getRefreshRoomsEvent());
    }

    @Override
    public void showGuestSearch(String query) {
        mEventBus.post(mEventFactory.getSearchGuestsEvent(query));
    }

    @Override
    public void refreshGuests() {
        mEventBus.post(mEventFactory.getRefreshGuestsEvent());
    }

    @Override
    public void hideActiveRoomSearch() {
        mEventBus.post(mEventFactory.getSearchRoomsDisableEvent());
    }

    @Override
    public void hideArchivedRoomSearch() {
        mEventBus.post(mEventFactory.getSearchRoomsDisableEvent());
    }

    @Override
    public void hideGuestSearch() {
        mEventBus.post(mEventFactory.getSearchGuestsDisableEvent());
    }
}
