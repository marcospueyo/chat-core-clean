package com.mph.chatcontrol.data;
/* Created by macmini on 07/08/2017. */

import java.util.List;

public interface GuestRepository {

    List<Guest> getGuests();

    Guest getGuest(String id);

    void updateGuest(Guest guest);
}
