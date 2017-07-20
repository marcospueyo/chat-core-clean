package com.mph.chatcontrol.guestlist.viewmodel.mapper;
/* Created by macmini on 20/07/2017. */

import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.data.Mapper;
import com.mph.chatcontrol.guestlist.viewmodel.GuestViewModel;

public class GuestViewModelToGuestMapper extends Mapper<GuestViewModel, Guest> {

    @Override
    public Guest map(GuestViewModel value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GuestViewModel reverseMap(Guest value) {
        GuestViewModel guestViewModel = GuestViewModel.builder()
                .setId(value.id())
                .build();
        return guestViewModel;
    }
}
