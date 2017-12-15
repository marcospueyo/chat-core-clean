package com.mph.chatcontrol.utils;
/* Created by macmini on 12/12/2017. */

import com.mph.chatcontrol.events.LogoutEvent;
import com.mph.chatcontrol.events.RefreshGuestsEvent;
import com.mph.chatcontrol.events.RefreshRoomsEvent;
import com.mph.chatcontrol.events.SearchGuestsDisableEvent;
import com.mph.chatcontrol.events.SearchGuestsEvent;
import com.mph.chatcontrol.events.SearchRoomsDisableEvent;
import com.mph.chatcontrol.events.SearchRoomsEvent;

public class EventFactoryImpl implements EventFactory {

    public EventFactoryImpl() {
    }

    @Override
    public Object getRefreshRoomsEvent() {
        return RefreshRoomsEvent.create();
    }

    @Override
    public Object getSearchRoomsEvent(String query) {
        return SearchRoomsEvent.create(query);
    }

    @Override
    public Object getRefreshGuestsEvent() {
        return RefreshGuestsEvent.create();
    }

    @Override
    public Object getSearchGuestsEvent(String query) {
        return SearchGuestsEvent.create(query);
    }

    @Override
    public Object getSearchRoomsDisableEvent() {
        return SearchRoomsDisableEvent.create();
    }

    @Override
    public Object getSearchGuestsDisableEvent() {
        return SearchGuestsDisableEvent.create();
    }

    @Override
    public Object getLogoutEvent() {
        return new LogoutEvent();
    }
}
