package com.mph.chatcontrol.events;
/* Created by macmini on 12/12/2017. */

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SearchGuestsEvent {

    public abstract String query();

    public static SearchGuestsEvent create(String query) {
        return new AutoValue_SearchGuestsEvent(query);
    }
}
