package com.mph.chatcontrol.data;
/* Created by macmini on 07/08/2017. */

import java.util.Date;

import io.requery.Entity;
import io.requery.Key;

@Entity
public abstract class AbstractGuest {

    @Key
    String id;

    String name;
    String phone;
    String email;
    Date startDate;
    Date endDate;

    String relatedRoomID;
}
