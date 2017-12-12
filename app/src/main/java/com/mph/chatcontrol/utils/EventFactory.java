package com.mph.chatcontrol.utils;
/* Created by macmini on 12/12/2017. */

public interface EventFactory {

    Object getRefreshRoomsEvent();

    Object getSearchRoomsEvent();

    Object getRefreshGuestsEvent();

    Object getSearchGuestsEvent();
}
