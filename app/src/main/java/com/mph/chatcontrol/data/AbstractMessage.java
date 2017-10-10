package com.mph.chatcontrol.data;
/* Created by Marcos on 04/08/2017.*/

import java.util.Date;

import io.requery.Entity;
import io.requery.Key;

@Entity
public abstract class AbstractMessage {

    @Key
    String id;

    String text;
    Date date;

    String roomId;
    String senderName;
    String senderId;
}
