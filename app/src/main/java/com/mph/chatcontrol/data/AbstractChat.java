package com.mph.chatcontrol.data;
/* Created by macmini on 03/08/2017. */

import java.util.Date;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

@Entity
public abstract class AbstractChat {

    @Key
    String id;

    String guestName;

    String title;
    String description;
    Integer pendingCount;
    Date startDate;
    Date endDate;
    Date lastMsgDate;
    String lastMsg;
    Boolean active;

}
