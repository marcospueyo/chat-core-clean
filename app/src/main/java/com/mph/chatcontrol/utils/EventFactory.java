package com.mph.chatcontrol.utils;
/* Created by macmini on 12/12/2017. */

public interface EventFactory {

    Object getRefreshRoomsEvent();

    Object getSearchRoomsEvent(String query);

    Object getSearchRoomsDisableEvent();

    Object getRefreshGuestsEvent();

    Object getSearchGuestsEvent(String query);

    Object getSearchGuestsDisableEvent();
}
