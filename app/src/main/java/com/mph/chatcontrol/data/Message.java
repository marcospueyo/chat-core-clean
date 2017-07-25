package com.mph.chatcontrol.data;
/* Created by macmini on 25/07/2017. */

import com.google.auto.value.AutoValue;

import java.util.Date;
import java.util.UUID;

@AutoValue
public abstract class Message {

    public static Message create(String id, String text, Date date) {
        return new AutoValue_Message(id, text, date);
    }

    public static Message create(String text, Date date) {
        return new AutoValue_Message(UUID.randomUUID().toString(), text, date);
    }

    public abstract String id();
    public abstract String text();
    public abstract Date date();
}
