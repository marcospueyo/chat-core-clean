package com.mph.chatcontrol.events;
/* Created by macmini on 13/12/2017. */

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SearchGuestsDisableEvent {

    public static SearchGuestsDisableEvent create() {
        return new AutoValue_SearchGuestsDisableEvent();
    }
}
