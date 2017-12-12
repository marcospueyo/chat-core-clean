package com.mph.chatcontrol.utils;
/* Created by macmini on 12/12/2017. */

import com.mph.chatcontrol.events.RefreshGuestsEvent;
import com.mph.chatcontrol.events.RefreshRoomsEvent;
import com.mph.chatcontrol.events.SearchGuestsEvent;
import com.mph.chatcontrol.events.SearchRoomsEvent;

public class EventFactoryImpl implements EventFactory {

    public EventFactoryImpl() {
    }

    @Override
    public Object getRefreshRoomsEvent() {
        return RefreshRoomsEvent.create();
    }

    @Override
    public Object getSearchRoomsEvent() {
        return SearchRoomsEvent.create();
    }

    @Override
    public Object getRefreshGuestsEvent() {
        return RefreshGuestsEvent.create();
    }

    @Override
    public Object getSearchGuestsEvent() {
        return SearchGuestsEvent.create();
    }
}
