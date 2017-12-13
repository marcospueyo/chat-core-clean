package com.mph.chatcontrol.events;
/* Created by macmini on 12/12/2017. */

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SearchRoomsEvent {

    public abstract String query();

    public static SearchRoomsEvent create(String query) {
        return new AutoValue_SearchRoomsEvent(query);
    }
}
