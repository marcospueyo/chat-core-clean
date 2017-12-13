package com.mph.chatcontrol.utils;
/* Created by macmini on 12/12/2017. */

public interface Router {

    void showActiveRoomSearch(String query);

    void hideActiveRoomSearch();

    void refreshActiveRooms();

    void showArchivedRoomSearch(String query);

    void hideArchivedRoomSearch();

    void refreshArchivedRooms();

    void showGuestSearch(String query);

    void hideGuestSearch();

    void refreshGuests();
}
