package com.mph.chatcontrol.network.message;

import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.data.Message;

/* Created by macmini on 09/10/2017. */

public class RestMessageToMessageMapper extends Mapper<RestMessage, Message> {

    @Override
    public Message map(RestMessage value) {
        return value.toMessage();
    }

    @Override
    public RestMessage reverseMap(Message value) {
        return new RestMessage(value);
    }
}
