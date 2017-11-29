package com.mph.chatcontrol.data;
/* Created by macmini on 03/08/2017. */

import java.util.Date;

import io.requery.Entity;
import io.requery.Key;

@Entity
public abstract class AbstractChat {

    @Key
    String id;
    String guestName;
    String propertyName;
    Integer messageCount;
    Date startDate;
    Date endDate;
    Date lastMsgDate;
    String lastMsg;
    String guestID;
}
