package com.mph.chatcontrol.data;
/* Created by macmini on 17/07/2017. */

import com.google.auto.value.AutoValue;

import java.util.Date;
import java.util.UUID;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

@Entity
@AutoValue
public abstract class Chat {
    public static Chat create(String title, String description, String id, Integer pendingCount,
                              Date startDate, Date endDate, Date lastMsgDate, String lastMsg,
                              Boolean active) {
        return new AutoValue_Chat(title, description, id, pendingCount, startDate, endDate,
                lastMsgDate, lastMsg, active);
    }

    @Key @Generated
    public abstract String id();

    public abstract String title();
    public abstract String description();
    public abstract Integer pendingCount();
    public abstract Date startDate();
    public abstract Date endDate();
    public abstract Date lastMsgDate();
    public abstract String lastMsg();
    public abstract Boolean active();
}
