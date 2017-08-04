package com.mph.chatcontrol.room.contract;
/* Created by macmini on 03/08/2017. */

public interface UpdateSeenStatusInteractor {

    interface OnFinishedListener {
        void onSeenStatusUpdated();
        void onSeenStatusUpdateError();
    }

    void execute(String roomID, boolean seen, OnFinishedListener listener);
}
