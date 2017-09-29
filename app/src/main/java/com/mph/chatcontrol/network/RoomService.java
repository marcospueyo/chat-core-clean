package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import java.util.List;

public interface RoomService {
    interface GetRoomsCallback {

        void onRoomsLoaded(List<RestRoom> rooms);

        void onDataNotAvailable();
    }

    void getRooms(GetRoomsCallback callback);

}
