package com.mph.chatcontrol.network;

import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.data.Mapper;

/* Created by macmini on 29/09/2017. */

public class RestGuestToGuestMapper extends Mapper<RestGuest, Guest> {

    @Override
    public Guest map(RestGuest value) {
        return value.toGuest();
    }

    @Override
    public RestGuest reverseMap(Guest value) {
        return new RestGuest(value);
    }
}
