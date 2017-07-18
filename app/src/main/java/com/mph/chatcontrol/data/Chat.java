package com.mph.chatcontrol.data;
/* Created by macmini on 17/07/2017. */

import com.google.auto.value.AutoValue;

import java.util.Date;
import java.util.UUID;

@AutoValue
public abstract class Chat {
    public static Chat create(String title, String description, String id, Integer pendingCount,
                              Date startDate, Date endDate) {
        return new AutoValue_Chat(title, description, id, pendingCount, startDate, endDate);
    }
    public static Chat create(String title, String description) {
        return new AutoValue_Chat(title, description, UUID.randomUUID().toString(), 0, null, null);
    }

    public static Chat create(String title, String description, Integer pendingCount,
                              Date startDate, Date endDate) {
        return new AutoValue_Chat(title, description, UUID.randomUUID().toString(), pendingCount,
                startDate, endDate);
    }

    public abstract String title();
    public abstract String description();
    public abstract String id();
    public abstract Integer pendingCount();
    public abstract Date startDate();
    public abstract Date endDate();
}
