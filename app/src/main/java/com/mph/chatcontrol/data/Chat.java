package com.mph.chatcontrol.data;
/* Created by macmini on 17/07/2017. */

import com.google.auto.value.AutoValue;

import java.util.UUID;

@AutoValue
public abstract class Chat {
    public static Chat create(String title, String description, String id) {
        return new AutoValue_Chat(title, description, id);
    }
    public static Chat create(String title, String description) {
        return new AutoValue_Chat(title, description, UUID.randomUUID().toString());
    }

    public abstract String title();
    public abstract String description();
    public abstract String id();
}
