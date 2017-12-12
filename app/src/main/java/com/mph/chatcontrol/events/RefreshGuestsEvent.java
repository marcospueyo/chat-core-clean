package com.mph.chatcontrol.events;
/* Created by macmini on 12/12/2017. */

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RefreshGuestsEvent {
    public static RefreshGuestsEvent create() {
        return new AutoValue_RefreshGuestsEvent();
    }
}
