package com.mph.chatcontrol.data;
/* Created by macmini on 19/07/2017. */

import com.google.auto.value.AutoValue;

import java.util.Date;
import java.util.UUID;

@AutoValue
public abstract class Guest {
    public static Guest create(String id, String name, String phone, String email,
                               Date startDate, Date endDate) {
        return new AutoValue_Guest(id, name, phone, email, startDate, endDate);
    }

    public static Guest create(String name, String phone, String email, Date startDate,
                               Date endDate) {
        return new AutoValue_Guest(UUID.randomUUID().toString(), name, phone, email, startDate,
                endDate);
    }

    public abstract String id();
    public abstract String name();
    public abstract String phone();
    public abstract String email();
    public abstract Date startDate();
    public abstract Date endDate();
}
