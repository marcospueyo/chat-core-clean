package com.mph.chatcontrol.network;

import com.mph.chatcontrol.data.Chat;
import com.mph.chatcontrol.data.Mapper;

/* Created by macmini on 31/08/2017. */

public class RestRoomToChatMapper extends Mapper<RestRoom, Chat> {

    @Override
    public Chat map(RestRoom value) {
        return value.toChat();
    }

    @Override
    public RestRoom reverseMap(Chat value) {
        return new RestRoom(value);
    }
}
