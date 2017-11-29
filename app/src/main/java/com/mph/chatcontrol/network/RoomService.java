package com.mph.chatcontrol.network;
/* Created by macmini on 31/08/2017. */

import java.util.List;
import java.util.Map;

public interface RoomService {
    interface GetRoomsCallback {

        void onRoomsLoaded(Map<String, RestRoom> roomMap);

        void onDataNotAvailable();
    }

    void getRooms(GetRoomsCallback callback);

}
