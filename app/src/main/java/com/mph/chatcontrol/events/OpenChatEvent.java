package com.mph.chatcontrol.events;
/* Created by macmini on 24/07/2017. */


import com.google.auto.value.AutoValue;

@AutoValue
public abstract class OpenChatEvent {

    public abstract String chatID();

    public static OpenChatEvent create(String chatID) {
        return new AutoValue_OpenChatEvent(chatID);
    }
}
