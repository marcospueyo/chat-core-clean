package com.mph.chatcontrol.data;

/* Created by Marcos on 28/11/2017.*/

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

@Entity
public abstract class AbstractChatInfo {

    @Key
    String id;

    String roomID;

    Integer readCount;

}
