package com.mph.chatcontrol.events;
/* Created by macmini on 13/12/2017. */

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SearchRoomsDisableEvent {

    public static SearchRoomsDisableEvent create() {
        return new AutoValue_SearchRoomsDisableEvent();
    }
}
