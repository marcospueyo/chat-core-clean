package com.mph.chatcontrol.device.screen;
/* Created by macmini on 11/12/2017. */

public interface ScreenSupervisor {

    void setRoomOnDisplay(String roomID);

    void deleteRoomOnDisplay(String roomID);

    boolean isRoomOnDisplay(String roomID);

}
